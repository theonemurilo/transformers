package com.aequilibrium.transformers.processor.fight

import com.aequilibrium.transformers.domain.FightResult
import com.aequilibrium.transformers.domain.Transformer
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(3)
class SkillFightRuleProcessor : FightRuleProcessor {

    override fun matchFightRule(autobot: Transformer, decepticon: Transformer): Boolean {
        return autobot.shouldWinBySkill(decepticon) || decepticon.shouldWinBySkill(autobot)
    }

    override fun getResult(autobot: Transformer, decepticon: Transformer): FightResult {
        return when {
            autobot.shouldWinBySkill(decepticon) -> FightResult(autobot, decepticon)
            else -> FightResult(decepticon, autobot)
        }
    }
}