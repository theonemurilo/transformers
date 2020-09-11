package com.aequilibrium.transformers.processor.fight

import spock.lang.Specification

import static com.aequilibrium.transformers.domain.FightStatus.TIE
import static com.aequilibrium.transformers.domain.FightStatus.WIN_LOSS
import static com.aequilibrium.transformers.domain.TransformerTeam.AUTOBOTS
import static com.aequilibrium.transformers.domain.TransformerTeam.DECEPTICONS
import static com.aequilibrium.transformers.fixture.TransformerFixture.getTransformer

class OverallRatingFightRuleProcessorTest extends Specification {

    private OverallRatingFightRuleProcessor processor = new OverallRatingFightRuleProcessor()

    def 'should always match for overall rating fight rule processor'() {
        when:
            def rule = processor.matchFightRule(getTransformer(1L, AUTOBOTS), getTransformer(2L, DECEPTICONS))

        then:
            rule
    }

    def 'should get result for overall rating fight rule processor'() {
        when:
            def result = processor.getResult(autobot, decepticon)

        then:
            result.fightStatus == fightStatus
            result.winner?.transformerTeam == winner
            result.loser?.transformerTeam == loser

        where:
            autobot                          | decepticon                          || fightStatus || winner      || loser
            getTransformer(10, 10, AUTOBOTS) | getTransformer(9, 10, DECEPTICONS)  || WIN_LOSS    || AUTOBOTS    || DECEPTICONS
            getTransformer(9, 10, AUTOBOTS)  | getTransformer(10, 10, DECEPTICONS) || WIN_LOSS    || DECEPTICONS || AUTOBOTS
            getTransformer(10, 10, AUTOBOTS) | getTransformer(10, 10, DECEPTICONS) || TIE         || null        || null
    }
}
