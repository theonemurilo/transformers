package com.aequilibrium.transformers.service

import com.aequilibrium.transformers.domain.TransformersBattle
import com.aequilibrium.transformers.domain.TransformersFightBattleResult
import com.aequilibrium.transformers.processor.battle.BattleRuleProcessor
import com.aequilibrium.transformers.processor.fight.FightRuleProcessor
import com.aequilibrium.transformers.response.BattleResponse
import com.aequilibrium.transformers.response.NoBattleResponse
import com.aequilibrium.transformers.response.TiedBattleResponse
import com.aequilibrium.transformers.response.WinLossBattleResponse
import javassist.NotFoundException
import org.springframework.stereotype.Service

@Service
class BattleService(
        private val transformersService: TransformersService,
        private val battleRuleProcessors: List<BattleRuleProcessor>,
        private val fightRuleProcessors: List<FightRuleProcessor>
) {

    fun battle(ids: List<Long>): BattleResponse {
        val transformers = transformersService.getAllByIds(ids).ifEmpty {
            throw NotFoundException("none of the transformer ids=$ids exist!")
        }

        val battle = TransformersBattle(
                transformers.filter { it.transformerTeam.isAutobot() }.sortedBy { it.rank },
                transformers.filter { it.transformerTeam.isDecepticon() }.sortedBy { it.rank }
        )

        return defineBattleWithoutAnyFight(battle) ?: defineBattleFighting(battle)
    }

    private fun defineBattleFighting(battle: TransformersBattle): BattleResponse {
        val fights = (0 until battle.numberOfFights()).map { i ->
            fightRuleProcessors.find {
                it.matchFightRule(battle.autobots[i], battle.decepticons[i])
            }!!.getResult(battle.autobots[i], battle.decepticons[i])
        }

        val battleResult = TransformersFightBattleResult(battle.numberOfFights(),
                battle.autobots,
                battle.decepticons,
                fights
        )

        return when {
            battleResult.isBattleTied() -> TiedBattleResponse.fromBattleResult(battleResult)
            else -> WinLossBattleResponse.fromBattleResult(battleResult)
        }
    }

    private fun defineBattleWithoutAnyFight(battle: TransformersBattle): NoBattleResponse? {
        return battleRuleProcessors.find {
            it.matchBattleRule(battle)
        }?.getResult()?.let {
            return NoBattleResponse(it.numberOfFights, it.allCompetitorsDestroyed)
        }
    }
}
