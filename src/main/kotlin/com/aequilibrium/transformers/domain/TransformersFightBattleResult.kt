package com.aequilibrium.transformers.domain

import com.aequilibrium.transformers.domain.TransformerTeam.AUTOBOTS
import com.aequilibrium.transformers.domain.TransformerTeam.DECEPTICONS

data class TransformersFightBattleResult(
        override val numberOfFights: Int,
        val autobots: List<Transformer>,
        val decepticons: List<Transformer>,
        val fightsResults: List<FightResult>
) : TransformersBattleResult(numberOfFights) {

    fun isBattleTied() = countAutobotsPoints() == countDecepticonsPoints()

    fun countTiedFights() = fightsResults.count { it.fightStatus == FightStatus.TIE }

    fun getAutobotWinners() = fightsResults.filter {
        it.winner != null && it.winner.transformerTeam.isAutobot()
    }.map { it.winner }

    fun getDecepticonWinners() = fightsResults.filter {
        it.winner != null && it.winner.transformerTeam.isDecepticon()
    }.map { it.winner }

    fun getWinnerTeam() = when {
        countAutobotsPoints() > countDecepticonsPoints() -> AUTOBOTS
        else -> DECEPTICONS
    }

    fun getWinnerTransformers() = when (getWinnerTeam()) {
        AUTOBOTS -> autobots
        DECEPTICONS -> decepticons
    }

    fun getLosingSurvivors() = when (getWinnerTeam()) {
        AUTOBOTS -> getSurvivors().plus(getSkippedDecepticons())
        DECEPTICONS -> getSurvivors().plus(getSkippedAutobots())
    }

    fun getAutobotSurvivorsForTiedBattle() = getAutobotWinners().plus(getSkippedAutobots())

    fun getDecepticonSurvivorsForTiedBattle() = getDecepticonWinners().plus(getSkippedDecepticons())

    private fun getSkippedAutobots() = when {
        autobots.isNotEmpty() -> autobots.subList(numberOfFights, autobots.size)
        else -> emptyList()
    }

    private fun getSkippedDecepticons() = when {
        decepticons.isNotEmpty() -> decepticons.subList(numberOfFights, decepticons.size)
        else -> emptyList()
    }

    private fun countAutobotsPoints() = fightsResults.filter { it.winner != null && it.winner.transformerTeam.isAutobot() }.count()

    private fun countDecepticonsPoints() = fightsResults.filter { it.winner != null && it.winner.transformerTeam.isDecepticon() }.count()

    private fun getSurvivors() = when (getWinnerTeam()) {
        AUTOBOTS -> fightsResults.filter { it.winner != null && it.winner.transformerTeam.isDecepticon() }.map { it.winner }
        DECEPTICONS -> fightsResults.filter { it.winner != null && it.winner.transformerTeam.isAutobot() }.map { it.winner }
    }
}