package com.aequilibrium.transformers.domain

data class TransformersNoFightBattleResult(
        override val numberOfFights: Int,
        val allCompetitorsDestroyed: Boolean
) : TransformersBattleResult(numberOfFights) {

    constructor(allCompetitorsDestroyed: Boolean) : this(0, allCompetitorsDestroyed)
}