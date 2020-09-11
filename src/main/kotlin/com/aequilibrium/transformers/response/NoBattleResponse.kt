package com.aequilibrium.transformers.response

import io.swagger.annotations.ApiModel

@ApiModel(parent = BattleResponse::class, description = "Response for no Battle")
class NoBattleResponse(
        override val numberOfFights: Int,
        val allCompetitorsDestroyed: Boolean
) : BattleResponse(numberOfFights)