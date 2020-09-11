package com.aequilibrium.transformers.processor.fight

import com.aequilibrium.transformers.domain.FightResult
import com.aequilibrium.transformers.domain.Transformer

interface FightRuleProcessor {

    fun matchFightRule(autobot: Transformer, decepticon: Transformer): Boolean

    fun getResult(autobot: Transformer, decepticon: Transformer): FightResult
}
