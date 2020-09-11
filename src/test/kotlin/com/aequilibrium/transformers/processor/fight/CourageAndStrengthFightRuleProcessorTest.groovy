package com.aequilibrium.transformers.processor.fight

import spock.lang.Specification

import static com.aequilibrium.transformers.domain.FightStatus.WIN_LOSS
import static com.aequilibrium.transformers.domain.TransformerTeam.AUTOBOTS
import static com.aequilibrium.transformers.domain.TransformerTeam.DECEPTICONS
import static com.aequilibrium.transformers.fixture.TransformerFixture.getTransformer

class CourageAndStrengthFightRuleProcessorTest extends Specification {

    private CourageAndStrengthFightRuleProcessor processor = new CourageAndStrengthFightRuleProcessor()

    def 'should match for courage and strength fight rule processor'() {
        expect:
            processor.matchFightRule(autobot, decepticon) == result

        where:
            autobot                          | decepticon                          || result
            getTransformer(10, 10, AUTOBOTS) | getTransformer(7, 6, DECEPTICONS)   || true
            getTransformer(7, 6, AUTOBOTS)   | getTransformer(10, 10, DECEPTICONS) || true
            getTransformer(7, 7, AUTOBOTS)   | getTransformer(10, 10, DECEPTICONS) || false
            getTransformer(6, 7, AUTOBOTS)   | getTransformer(10, 10, DECEPTICONS) || false
            getTransformer(10, 10, AUTOBOTS) | getTransformer(6, 7, DECEPTICONS)   || false
            getTransformer(10, 10, AUTOBOTS) | getTransformer(6, 7, DECEPTICONS)   || false
            getTransformer(10, 10, AUTOBOTS) | getTransformer(10, 10, DECEPTICONS) || false
    }

    def 'should get result for courage and strength fight rule processor'() {
        when:
            def result = processor.getResult(autobot, decepticon)

        then:
            result.fightStatus == fightStatus
            result.winner.transformerTeam == winner
            result.loser.transformerTeam == loser

        where:
            autobot                          | decepticon                          || fightStatus || winner      || loser
            getTransformer(10, 10, AUTOBOTS) | getTransformer(7, 6, DECEPTICONS)   || WIN_LOSS    || AUTOBOTS    || DECEPTICONS
            getTransformer(7, 6, AUTOBOTS)   | getTransformer(10, 10, DECEPTICONS) || WIN_LOSS    || DECEPTICONS || AUTOBOTS
    }
}
