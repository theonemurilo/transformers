package com.aequilibrium.transformers.domain


import spock.lang.Specification

import static com.aequilibrium.transformers.domain.TransformerTeam.AUTOBOTS
import static com.aequilibrium.transformers.domain.TransformerTeam.DECEPTICONS
import static com.aequilibrium.transformers.fixture.FightResultFixture.getAutobotWinnerFightResult
import static com.aequilibrium.transformers.fixture.FightResultFixture.getDecepticonWinnerFightResult
import static com.aequilibrium.transformers.fixture.FightResultFixture.getTiedFightResult
import static com.aequilibrium.transformers.fixture.TransformerFixture.getTransformer

class TransformersFightBattleResultTest extends Specification {

    def 'should return correct result when battle is tied'() {
        expect:
            new TransformersFightBattleResult(fights.size(), [], [], fights).isBattleTied() == isBattleTied

        where:
            fights                                                                                  || isBattleTied
            [getAutobotWinnerFightResult(), getDecepticonWinnerFightResult(), getTiedFightResult()] || true
            [getAutobotWinnerFightResult(), getTiedFightResult()]                                   || false
            [getDecepticonWinnerFightResult(), getTiedFightResult()]                                || false
            [getAutobotWinnerFightResult(), getDecepticonWinnerFightResult()]                       || true
    }

    def 'should count the quantity of tied fights'() {
        expect:
            new TransformersFightBattleResult(fights.size(), [], [], fights).countTiedFights() == tiedFightsQuantity

        where:
            fights                                                                                  || tiedFightsQuantity
            [getTiedFightResult(), getDecepticonWinnerFightResult(), getAutobotWinnerFightResult()] || 1
            [getTiedFightResult(), getTiedFightResult(), getAutobotWinnerFightResult()]             || 2
            [getAutobotWinnerFightResult(), getDecepticonWinnerFightResult()]                       || 0
            [getTiedFightResult(), getTiedFightResult(), getTiedFightResult()]                      || 3
    }

    def 'should get list of winner autobots'() {
        given:
            def fights = [getTiedFightResult(), getDecepticonWinnerFightResult(),
                          getAutobotWinnerFightResult(), getAutobotWinnerFightResult(),
                          getAutobotWinnerFightResult(), getDecepticonWinnerFightResult()]
        when:
            def winners = new TransformersFightBattleResult(fights.size(), [], [], fights).getAutobotWinners()

        then:
            winners.every { it.transformerTeam == AUTOBOTS }
    }

    def 'should get list of winner decepticons'() {
        given:
            def fights = [getTiedFightResult(), getDecepticonWinnerFightResult(),
                          getAutobotWinnerFightResult(), getAutobotWinnerFightResult(),
                          getAutobotWinnerFightResult(), getDecepticonWinnerFightResult()]
        when:
            def winners = new TransformersFightBattleResult(fights.size(), [], [], fights).getDecepticonWinners()

        then:
            winners.every { it.transformerTeam == TransformerTeam.DECEPTICONS }
    }

    def 'should get autobots as winner team'() {
        given:
            def fights = [getTiedFightResult(), getDecepticonWinnerFightResult(),
                          getAutobotWinnerFightResult(), getAutobotWinnerFightResult(),
                          getAutobotWinnerFightResult(), getDecepticonWinnerFightResult()]
        when:
            def winner = new TransformersFightBattleResult(fights.size(), [], [], fights).getWinnerTeam()

        then:
            winner == AUTOBOTS
    }

    def 'should get decepticons as winner team'() {
        given:
            def fights = [getTiedFightResult(), getDecepticonWinnerFightResult(),
                          getAutobotWinnerFightResult(), getDecepticonWinnerFightResult(),
                          getAutobotWinnerFightResult(), getDecepticonWinnerFightResult()]
        when:
            def winner = new TransformersFightBattleResult(fights.size(), [], [], fights).getWinnerTeam()

        then:
            winner == DECEPTICONS
    }

    def 'should get all autobots when they are the winners'() {
        given:
            def autobotWinnerFight1 = getAutobotWinnerFightResult()
            def autobotWinnerFight2 = getAutobotWinnerFightResult()
            def autobots = [autobotWinnerFight1.winner, autobotWinnerFight2.winner]
            def decepticons = [autobotWinnerFight1.loser, autobotWinnerFight2.loser]
            def fights = [autobotWinnerFight1, autobotWinnerFight2]
        when:
            def winners = new TransformersFightBattleResult(fights.size(), autobots, decepticons, fights).getWinnerTransformers()

        then:
            winners.every { it.transformerTeam == AUTOBOTS }
            winners.size() == autobots.size()
    }

    def 'should get all decepticons when they are the winners'() {
        given:
            def decepticonWinnerFight1 = getDecepticonWinnerFightResult()
            def decepticonWinnerFight2 = getDecepticonWinnerFightResult()
            def autobots = [decepticonWinnerFight1.loser, decepticonWinnerFight1.loser]
            def decepticons = [decepticonWinnerFight1.winner, decepticonWinnerFight1.winner]
            def fights = [decepticonWinnerFight1, decepticonWinnerFight2]
        when:
            def winners = new TransformersFightBattleResult(fights.size(), autobots, decepticons, fights).getWinnerTransformers()

        then:
            winners.every { it.transformerTeam == DECEPTICONS }
            winners.size() == decepticons.size()
    }

    def 'should get autobots survivors for tied battle'() {
        given:
            def autobotWinnerFight1 = getAutobotWinnerFightResult()
            def autobotWinnerFight2 = getAutobotWinnerFightResult()
            def decepticonWinnerFight1 = getDecepticonWinnerFightResult()
            def decepticonWinnerFight2 = getDecepticonWinnerFightResult()
            def autobots = [autobotWinnerFight1.winner, autobotWinnerFight2.winner,
                            decepticonWinnerFight1.loser, decepticonWinnerFight2.loser]
            def decepticons = [decepticonWinnerFight1.winner, decepticonWinnerFight2.winner,
                               autobotWinnerFight1.loser, autobotWinnerFight2.loser]
            def fights = [autobotWinnerFight1, autobotWinnerFight2, decepticonWinnerFight1, decepticonWinnerFight2]
        when:
            def winners = new TransformersFightBattleResult(fights.size(), autobots, decepticons, fights).getAutobotSurvivorsForTiedBattle()

        then:
            winners.every { it.transformerTeam == AUTOBOTS }
            winners.size() == 2
    }

    def 'should get decepticons survivors for tied battle'() {
        given:
            def autobotWinnerFight1 = getAutobotWinnerFightResult()
            def autobotWinnerFight2 = getAutobotWinnerFightResult()
            def decepticonWinnerFight1 = getDecepticonWinnerFightResult()
            def decepticonWinnerFight2 = getDecepticonWinnerFightResult()
            def autobots = [autobotWinnerFight1.winner, autobotWinnerFight2.winner,
                            decepticonWinnerFight1.loser, decepticonWinnerFight2.loser]
            def decepticons = [decepticonWinnerFight1.winner, decepticonWinnerFight2.winner,
                               autobotWinnerFight1.loser, autobotWinnerFight2.loser]
            def fights = [autobotWinnerFight1, autobotWinnerFight2, decepticonWinnerFight1, decepticonWinnerFight2]
        when:
            def winners = new TransformersFightBattleResult(fights.size(), autobots, decepticons, fights).getDecepticonSurvivorsForTiedBattle()

        then:
            winners.every { it.transformerTeam == DECEPTICONS }
            winners.size() == 2
    }

    def 'should get decepticons losing survivors'() {
        given:
            def autobotWinnerFight1 = getAutobotWinnerFightResult()
            def autobotWinnerFight2 = getAutobotWinnerFightResult()
            def autobotWinnerFight3 = getAutobotWinnerFightResult()
            def decepticonWinnerFight1 = getDecepticonWinnerFightResult()
            def decepticonWinnerFight2 = getDecepticonWinnerFightResult()

        and:
            def autobots = [autobotWinnerFight1.winner, autobotWinnerFight2.winner, autobotWinnerFight3.winner,
                            decepticonWinnerFight1.loser, decepticonWinnerFight2.loser]
        and:
            def skippedDecepticon = getTransformer(3L, DECEPTICONS)
        and:
            def decepticons = [decepticonWinnerFight1.winner, decepticonWinnerFight2.winner, autobotWinnerFight3.loser,
                               autobotWinnerFight1.loser, autobotWinnerFight2.loser, skippedDecepticon]
        and:
            def fights = [autobotWinnerFight1, autobotWinnerFight2, autobotWinnerFight3,
                          decepticonWinnerFight1, decepticonWinnerFight2]
        when:
            def winners = new TransformersFightBattleResult(fights.size(), autobots, decepticons, fights).getLosingSurvivors()

        then:
            winners.every { it.transformerTeam == DECEPTICONS }
            winners.size() == 3
    }

    def 'should get autobots losing survivors'() {
        given:
            def autobotWinnerFight1 = getAutobotWinnerFightResult()
            def autobotWinnerFight2 = getAutobotWinnerFightResult()
            def decepticonWinnerFight1 = getDecepticonWinnerFightResult()
            def decepticonWinnerFight2 = getDecepticonWinnerFightResult()
            def decepticonWinnerFight3 = getDecepticonWinnerFightResult()

        and:
            def skippedAutobot = getTransformer(3L, AUTOBOTS)
        and:
            def autobots = [autobotWinnerFight1.winner, autobotWinnerFight2.winner, decepticonWinnerFight3.loser,
                            decepticonWinnerFight1.loser, decepticonWinnerFight2.loser, skippedAutobot]
        and:
            def decepticons = [decepticonWinnerFight1.winner, decepticonWinnerFight2.winner, decepticonWinnerFight3.winner,
                               autobotWinnerFight1.loser, autobotWinnerFight2.loser]
        and:
            def fights = [autobotWinnerFight1, autobotWinnerFight2, decepticonWinnerFight1,
                          decepticonWinnerFight2, decepticonWinnerFight3]
        when:
            def winners = new TransformersFightBattleResult(fights.size(), autobots, decepticons, fights).getLosingSurvivors()

        then:
            winners.every { it.transformerTeam == AUTOBOTS }
            winners.size() == 3
    }
}
