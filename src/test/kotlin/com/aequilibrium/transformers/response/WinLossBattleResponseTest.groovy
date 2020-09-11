package com.aequilibrium.transformers.response


import com.aequilibrium.transformers.domain.TransformersFightBattleResult
import spock.lang.Specification

import static com.aequilibrium.transformers.domain.TransformerTeam.AUTOBOTS
import static com.aequilibrium.transformers.fixture.FightResultFixture.getAutobotWinnerFightResult

class WinLossBattleResponseTest extends Specification {

    def 'should get a win loss battle response'() {
        given:
            def fightResult = getAutobotWinnerFightResult()
            def fightResults = [fightResult]
            def autobot = fightResult.winner
            def decepticon = fightResult.loser
            def battleResult = new TransformersFightBattleResult(fightResults.size(), [autobot], [decepticon], fightResults)

        when:
            def battleResponse = new WinLossBattleResponse.Companion().fromBattleResult(battleResult)

        then:
            battleResponse.winningTransformers.every { it.id == autobot.id }
            battleResponse.winningTeamName == AUTOBOTS.teamName
            battleResponse.losingTeamSurvivors.every { it.id == decepticon.id }
            battleResponse.numberOfFights == 1
    }
}
