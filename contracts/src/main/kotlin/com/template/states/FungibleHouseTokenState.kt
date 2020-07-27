package com.template.states

import com.r3.corda.lib.tokens.contracts.states.EvolvableTokenType
import com.template.contracts.HouseTokenStateContract
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party
import java.math.BigDecimal

// *********
// * State *
// *********
@BelongsToContract(HouseTokenStateContract::class)
data class FungibleHouseTokenState(val valuation: BigDecimal,
                                   val maintainer: Party,
                                   val symbol:String,
                                   override val fractionDigits: Int,
                                   override val linearId: UniqueIdentifier,
                                   override val maintainers: List<Party> = listOf(maintainer)
) : EvolvableTokenType()