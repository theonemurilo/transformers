package com.aequilibrium.transformers.processor.fight


import spock.lang.Specification

import static com.aequilibrium.transformers.domain.FightStatus.WIN_LOSS
import static com.aequilibrium.transformers.domain.TransformerTeam.AUTOBOTS
import static com.aequilibrium.transformers.domain.TransformerTeam.DECEPTICONS
import static com.aequilibrium.transformers.fixture.TransformerFixture.getPredakin
import static com.aequilibrium.transformers.fixture.TransformerFixture.getPrime
import static com.aequilibrium.transformers.fixture.TransformerFixture.getTransformer

class AutomaticallyWinLossFightRuleProcessorTest extends Specification {

    private AutomaticallyWinLossFightRuleProcessor processor = new AutomaticallyWinLossFightRuleProcessor()

    def 'should match automatically win loss fight rule processor'() {
        expect:
            processor.matchFightRule(autobot, decepticon) == result

        where:
            autobot                      | decepticon                      || result
            getPrime()                   | getTransformer(2L, DECEPTICONS) || true
            getTransformer(1L, AUTOBOTS) | getPredakin()                   || true
            getTransformer(1L, AUTOBOTS) | getTransformer(2L, DECEPTICONS) || false
    }

    def 'should get result for win loss automatically fight rule processor'() {
        when:
            def result = processor.getResult(autobot, decepticon)

        then:
            result.fightStatus == fightStatus
            result.winner.transformerTeam == winner
            result.loser.transformerTeam == loser

        where:
            autobot                      | decepticon                      || fightStatus || winner      || loser
            getPrime()                   | getTransformer(2L, DECEPTICONS) || WIN_LOSS    || AUTOBOTS    || DECEPTICONS
            getTransformer(1L, AUTOBOTS) | getPredakin()                   || WIN_LOSS    || DECEPTICONS || AUTOBOTS
    }
}
