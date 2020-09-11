package com.aequilibrium.transformers.processor.battle

import com.aequilibrium.transformers.domain.TransformersBattle
import com.aequilibrium.transformers.domain.TransformersNoFightBattleResult

interface BattleRuleProcessor {

    fun getResult(): TransformersNoFightBattleResult

    fun matchBattleRule(battle: TransformersBattle): Boolean
}