package com.aequilibrium.transformers.domain

import kotlin.math.min

data class TransformersBattle(
        val autobots: List<Transformer>,
        val decepticons: List<Transformer>
) {
    fun numberOfFights() = min(autobots.size, decepticons.size)

    fun shouldAllBeDestroyed(): Boolean {
        return (0 until numberOfFights()).any { i ->
            autobots[i].shouldWinAutomatically() && decepticons[i].shouldWinAutomatically()
        }
    }
}