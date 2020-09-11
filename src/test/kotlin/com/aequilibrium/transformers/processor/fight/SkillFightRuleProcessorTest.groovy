package com.aequilibrium.transformers.processor.fight

import spock.lang.Specification

import static com.aequilibrium.transformers.domain.FightStatus.WIN_LOSS
import static com.aequilibrium.transformers.domain.TransformerTeam.AUTOBOTS
import static com.aequilibrium.transformers.domain.TransformerTeam.DECEPTICONS
import static com.aequilibrium.transformers.fixture.TransformerFixture.getTransformer

class SkillFightRuleProcessorTest extends Specification {

    private SkillFightRuleProcessor processor = new SkillFightRuleProcessor()

    def 'should match for skill rating fight rule processor'() {
        expect:
            processor.matchFightRule(autobot, decepticon) == result

        where:
            autobot                      | decepticon                      || result
            getTransformer(10, AUTOBOTS) | getTransformer(7, DECEPTICONS)  || true
            getTransformer(7, AUTOBOTS)  | getTransformer(10, DECEPTICONS) || true
            getTransformer(10, AUTOBOTS) | getTransformer(10, DECEPTICONS) || false
    }

    def 'should get result for overall rating fight rule processor'() {
        when:
            def result = processor.getResult(autobot, decepticon)

        then:
            result.fightStatus == fightStatus
            result.winner?.transformerTeam == winner
            result.loser?.transformerTeam == loser

        where:
            autobot                      | decepticon                      || fightStatus || winner      || loser
            getTransformer(10, AUTOBOTS) | getTransformer(7, DECEPTICONS)  || WIN_LOSS    || AUTOBOTS    || DECEPTICONS
            getTransformer(7, AUTOBOTS)  | getTransformer(10, DECEPTICONS) || WIN_LOSS    || DECEPTICONS || AUTOBOTS
    }
}
