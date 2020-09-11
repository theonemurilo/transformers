package com.aequilibrium.transformers.response

import com.aequilibrium.transformers.domain.TransformersFightBattleResult
import spock.lang.Specification

import static com.aequilibrium.transformers.domain.TransformerTeam.AUTOBOTS
import static com.aequilibrium.transformers.domain.TransformerTeam.DECEPTICONS
import static com.aequilibrium.transformers.fixture.FightResultFixture.getAutobotWinnerFightResult
import static com.aequilibrium.transformers.fixture.FightResultFixture.getDecepticonWinnerFightResult
import static com.aequilibrium.transformers.fixture.FightResultFixture.getTiedFightResult
import static com.aequilibrium.transformers.fixture.TransformerFixture.getTransformer

class TiedBattleResponseTest extends Specification {

    def 'should get a tied battle response when there is no winners nor survivors'() {
        given:
            def fightResult = getTiedFightResult()
            def fightResults = [fightResult]
            def autobot = getTransformer(1L, AUTOBOTS)
            def decepticon = getTransformer(1L, DECEPTICONS)
            def battleResult = new TransformersFightBattleResult(fightResults.size(), [autobot], [decepticon], fightResults)

        when:
            def battleResponse = new TiedBattleResponse.Companion().fromBattleResult(battleResult)

        then:
            battleResponse.numberOfFights == 1
            battleResponse.numberOfFightsTied == 1
            battleResponse.decepticonWinners.isEmpty()
            battleResponse.decepticonSurvivors.isEmpty()
            battleResponse.autobotWinners.isEmpty()
            battleResponse.autobotSurvivors.isEmpty()
    }

    def 'should get a tied battle response when teams have survivors'() {
        given:
            def tiedFightResult = getTiedFightResult()
            def fightResult1 = getAutobotWinnerFightResult()
            def fightResult2 = getDecepticonWinnerFightResult()
            def fightResults = [tiedFightResult, fightResult1, fightResult2]
            def autobotDestroyedForTiedBattle = getTransformer(5L, AUTOBOTS)
            def decepticonDestroyed = getTransformer(6L, DECEPTICONS)
            def autobotLoser = fightResult2.loser
            def decepticonLoser = fightResult1.loser
            def autobotSurvivor = fightResult1.winner
            def decepticonSurvivor = fightResult2.winner
            def autobots = [autobotDestroyedForTiedBattle, autobotLoser, autobotSurvivor]
            def decepticons = [decepticonDestroyed, decepticonLoser, decepticonSurvivor]
            def battleResult = new TransformersFightBattleResult(fightResults.size(), autobots, decepticons, fightResults)

        when:
            def battleResponse = new TiedBattleResponse.Companion().fromBattleResult(battleResult)

        then:
            battleResponse.numberOfFights == 3
            battleResponse.numberOfFightsTied == 1
            battleResponse.decepticonWinners.every { it.id == decepticonSurvivor.id }
            battleResponse.decepticonSurvivors.every { it.id == decepticonSurvivor.id }
            battleResponse.autobotWinners.every { it.id == autobotSurvivor.id }
            battleResponse.autobotSurvivors.every { it.id == autobotSurvivor.id }
    }

    def 'should get a tied battle response when teams have survivors and skipped'() {
        given:
            def tiedFightResult = getTiedFightResult()
            def fightResult1 = getAutobotWinnerFightResult()
            def fightResult2 = getDecepticonWinnerFightResult()
            def fightResults = [tiedFightResult, fightResult1, fightResult2]
            def autobotDestroyedForTiedBattle = getTransformer(5L, AUTOBOTS)
            def decepticonDestroyed = getTransformer(6L, DECEPTICONS)
            def autobotLoser = fightResult2.loser
            def decepticonLoser = fightResult1.loser
            def autobotSurvivor = fightResult1.winner
            def decepticonSurvivor = fightResult2.winner
            def autobotSkipped = getTransformer(7L, AUTOBOTS)
            def autobots = [autobotDestroyedForTiedBattle, autobotLoser, autobotSurvivor, autobotSkipped]
            def decepticons = [decepticonDestroyed, decepticonLoser, decepticonSurvivor]
            def battleResult = new TransformersFightBattleResult(fightResults.size(), autobots, decepticons, fightResults)

        when:
            def battleResponse = new TiedBattleResponse.Companion().fromBattleResult(battleResult)

        then:
            battleResponse.numberOfFights == 3
            battleResponse.numberOfFightsTied == 1
            battleResponse.decepticonWinners.every { it.id == decepticonSurvivor.id }
            battleResponse.decepticonSurvivors.every { it.id == decepticonSurvivor.id }
            battleResponse.autobotWinners.every { it.id == autobotSurvivor.id }
            battleResponse.autobotSurvivors.size() == 2
            battleResponse.autobotSurvivors.any { it.id == autobotSurvivor.id }
            battleResponse.autobotSurvivors.any { it.id == autobotSkipped.id }
    }
}
