package com.aequilibrium.transformers.processor.battle

import com.aequilibrium.transformers.domain.TransformersBattle
import com.aequilibrium.transformers.domain.TransformersNoFightBattleResult
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(2)
class AllDestroyedBattleRuleProcessor : BattleRuleProcessor {

    override fun getResult(): TransformersNoFightBattleResult {
        return TransformersNoFightBattleResult(true)
    }

    override fun matchBattleRule(battle: TransformersBattle): Boolean {
        return battle.shouldAllBeDestroyed()
    }
}