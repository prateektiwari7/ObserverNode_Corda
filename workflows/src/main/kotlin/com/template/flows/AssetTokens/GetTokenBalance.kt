package com.template.flows.AssetTokens


import co.paralleluniverse.fibers.Suspendable
import com.r3.corda.lib.tokens.contracts.types.TokenType
import com.r3.corda.lib.tokens.workflows.utilities.tokenBalance
import com.template.states.FungibleHouseTokenState
import net.corda.core.contracts.Amount
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.node.services.queryBy
import net.corda.core.utilities.ProgressTracker

@InitiatingFlow
@StartableByRPC
class GetTokenBalance(val symbol:String) : FlowLogic<String>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call():String {
        //get house states on ledger with uuid as input tokenId
        val stateAndRef = serviceHub.vaultService.queryBy<FungibleHouseTokenState>()
                .states.filter { it.state.data.symbol.equals(symbol) }[0]

        //get the Token State object
        val evolvableTokenType = stateAndRef.state.data
        //get the pointer pointer to the house
        val tokenPointer = evolvableTokenType.toPointer(evolvableTokenType.javaClass)

        //retrieve amount
        val amount: Amount<TokenType> = serviceHub.vaultService.tokenBalance(tokenPointer)

        return "\n You currently have " + amount.quantity + " " + symbol + " Tokens\n"

    }
}