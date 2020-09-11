package com.aequilibrium.transformers.processor.fight

import com.aequilibrium.transformers.domain.FightResult
import com.aequilibrium.transformers.domain.Transformer
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(1)
class AutomaticallyWinLossFightRuleProcessor : FightRuleProcessor {

    override fun matchFightRule(autobot: Transformer, decepticon: Transformer): Boolean {
        return autobot.shouldWinAutomatically() || decepticon.shouldWinAutomatically()
    }

    override fun getResult(autobot: Transformer, decepticon: Transformer): FightResult {
        return when {
            autobot.shouldWinAutomatically() -> FightResult(autobot, decepticon)
            else -> FightResult(decepticon, autobot)
        }
    }
}