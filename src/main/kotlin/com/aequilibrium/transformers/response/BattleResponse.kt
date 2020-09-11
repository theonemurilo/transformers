package com.aequilibrium.transformers.response

import io.swagger.annotations.ApiModel

@ApiModel(subTypes = [NoBattleResponse::class, TiedBattleResponse::class, WinLossBattleResponse::class],
        discriminator = "type", description = "Super type of all Battle Responses")
abstract class BattleResponse(
        open val numberOfFights: Int
)