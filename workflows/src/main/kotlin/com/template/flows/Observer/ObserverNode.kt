package com.template.flows.Observer

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.node.StatesToRecord
import net.corda.core.transactions.SignedTransaction

@InitiatingFlow
class ReportToRegulatorFlow(private val regulator: Party, private val finalTx: SignedTransaction) : FlowLogic<Unit>() {
    @Suspendable
    override fun call() {
        val session = initiateFlow(regulator)
        subFlow(SendTransactionFlow(session, finalTx))
    }
}

@InitiatedBy(ReportToRegulatorFlow::class)
class ReceiveRegulatoryReportFlow(private val otherSideSession: FlowSession) : FlowLogic<Unit>() {
    @Suspendable
    override fun call() {
        // Start the matching side of SendTransactionFlow above, but tell it to record all visible states even
        // though they (as far as the node can tell) are nothing to do with us.
        subFlow(ReceiveTransactionFlow(otherSideSession, true, StatesToRecord.ALL_VISIBLE))
    }
}