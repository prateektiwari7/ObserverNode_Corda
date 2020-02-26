package com.template.contracts

import net.corda.core.contracts.CommandData
import net.corda.core.contracts.requireSingleCommand
import net.corda.core.contracts.requireThat
import net.corda.core.transactions.LedgerTransaction

class SendContract : net.corda.core.contracts.Contract {

    companion object{
        const val ID = "com.template.contracts.SendContract"
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<Commands.Create>()
        requireThat {
        }
    }

    interface Commands : CommandData {
        class Create : SendContract.Commands;
    }
}

