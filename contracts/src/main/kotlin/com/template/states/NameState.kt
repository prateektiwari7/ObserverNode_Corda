package com.template.states

import com.template.contracts.SendContract
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.ContractState
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party

@BelongsToContract(SendContract::class)
data class NameState(val bal: Int,
                     val party : Party
     ) : ContractState {

    override val participants: List<AbstractParty> = listOf(party);
    //To change initializer of created properties use File | Settings | File Templates.

}