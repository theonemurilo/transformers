package com.aequilibrium.transformers.response

import com.aequilibrium.transformers.domain.TransformersFightBattleResult
import com.aequilibrium.transformers.response.TransformerResponse.Companion.fromDomain
import io.swagger.annotations.ApiModel

@ApiModel(parent = BattleResponse::class, description = "Response for Win-Loss Battle")
data class WinLossBattleResponse(
        override val numberOfFights: Int,
        val winningTeamName: String,
        val winningTransformers: List<TransformerResponse>,
        val losingTeamSurvivors: List<TransformerResponse>

) : BattleResponse(numberOfFights) {

    companion object {
        fun fromBattleResult(battleResult: TransformersFightBattleResult): WinLossBattleResponse {
            return WinLossBattleResponse(battleResult.numberOfFights,
                    battleResult.getWinnerTeam().teamName,
                    battleResult.getWinnerTransformers().map { fromDomain(it) },
                    battleResult.getLosingSurvivors().map { fromDomain(it!!) }
            )
        }
    }
}