package com.aequilibrium.transformers.processor.battle

import com.aequilibrium.transformers.domain.TransformersBattle
import com.aequilibrium.transformers.domain.TransformersNoFightBattleResult
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(1)
class NoFightsBattleRuleProcessor : BattleRuleProcessor {

    override fun getResult(): TransformersNoFightBattleResult {
        return TransformersNoFightBattleResult(false)
    }

    override fun matchBattleRule(battle: TransformersBattle): Boolean {
        return battle.numberOfFights() == 0
    }
}