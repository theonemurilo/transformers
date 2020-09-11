package com.aequilibrium.transformers.processor.fight

import com.aequilibrium.transformers.domain.FightResult
import com.aequilibrium.transformers.domain.FightStatus.TIE
import com.aequilibrium.transformers.domain.Transformer
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(4)
class OverallRatingFightRuleProcessor : FightRuleProcessor {

    override fun matchFightRule(autobot: Transformer, decepticon: Transformer): Boolean {
        return true
    }

    override fun getResult(autobot: Transformer, decepticon: Transformer): FightResult {
        return when {
            autobot.hasGreaterOverallThen(decepticon) -> FightResult(autobot, decepticon)
            decepticon.hasGreaterOverallThen(autobot) -> FightResult(decepticon, autobot)
            else -> FightResult(TIE)
        }
    }
}