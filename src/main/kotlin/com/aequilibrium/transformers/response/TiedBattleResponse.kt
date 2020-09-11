package com.aequilibrium.transformers.response

import com.aequilibrium.transformers.domain.TransformersFightBattleResult
import com.aequilibrium.transformers.response.TransformerResponse.Companion.fromDomain
import io.swagger.annotations.ApiModel

@ApiModel(parent = BattleResponse::class, description = "Response for Tied Battle")
data class TiedBattleResponse(
        override val numberOfFights: Int,
        val numberOfFightsTied: Int,
        val autobotWinners: List<TransformerResponse>,
        val autobotSurvivors: List<TransformerResponse>,
        val decepticonWinners: List<TransformerResponse>,
        val decepticonSurvivors: List<TransformerResponse>

) : BattleResponse(numberOfFights) {

    companion object {
        fun fromBattleResult(battleResult: TransformersFightBattleResult): TiedBattleResponse {
            return TiedBattleResponse(battleResult.numberOfFights,
                    battleResult.countTiedFights(),
                    battleResult.getAutobotWinners().map { fromDomain(it!!) },
                    battleResult.getAutobotSurvivorsForTiedBattle().map { fromDomain(it!!) },
                    battleResult.getDecepticonWinners().map { fromDomain(it!!) },
                    battleResult.getDecepticonSurvivorsForTiedBattle().map { fromDomain(it!!) }
            )
        }
    }
}