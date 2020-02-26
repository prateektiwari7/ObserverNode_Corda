package com.template.flows.AddTransaction

import co.paralleluniverse.fibers.Suspendable
import com.template.contracts.SendContract
import com.template.flows.Observer.ReportToRegulatorFlow
import com.template.states.NameState
import net.corda.core.contracts.Command
import net.corda.core.contracts.requireThat
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

@InitiatingFlow
@StartableByRPC
class AddName(var bal: Int, var party: Party) : FlowLogic<SignedTransaction>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call() : SignedTransaction {

        val notary = serviceHub.networkMapCache.notaryIdentities.first()
        val command = Command(SendContract.Commands.Create(), listOf(party).map { it.owningKey })
        val namestate = NameState(bal,party)

        val txBuilder = TransactionBuilder(notary)
                .addOutputState(namestate, SendContract.ID)
                .addCommand(command)
        txBuilder.verify(serviceHub)
        val tx = serviceHub.signInitialTransaction(txBuilder)

        val sessions = (namestate.participants - ourIdentity).map { initiateFlow(it as Party) }
        val stx = subFlow(CollectSignaturesFlow(tx, sessions))
        return subFlow(FinalityFlow(stx, sessions))

    }
}

@InitiatedBy(AddName::class)
class AddName_Responder(val counterpartySession: FlowSession) : FlowLogic<SignedTransaction>() {
    @Suspendable
    override fun call() : SignedTransaction {
        val signedTransactionFlow = object : SignTransactionFlow(counterpartySession) {
            override fun checkTransaction(stx: SignedTransaction) = requireThat {
                val output = stx.tx.outputs.single().data

            }
        }
        val txWeJustSignedId = subFlow(signedTransactionFlow)

        val regulator = serviceHub.identityService.partiesFromName("PartyC", true).single()

        subFlow(ReportToRegulatorFlow(regulator, txWeJustSignedId))
        return subFlow(ReceiveFinalityFlow(counterpartySession, txWeJustSignedId.id))
    }
}
