package com.aequilibrium.transformers.processor.battle


import spock.lang.Specification

import static com.aequilibrium.transformers.fixture.TransformersBattleFixture.getAllDestroyedTransformersBattle
import static com.aequilibrium.transformers.fixture.TransformersBattleFixture.getOnePrimeWithTransformerBattle
import static com.aequilibrium.transformers.fixture.TransformersBattleFixture.getRegularTransformerBattle

class AllDestroyedBattleRuleProcessorTest extends Specification {

    private AllDestroyedBattleRuleProcessor processor = new AllDestroyedBattleRuleProcessor()

    def 'should match for all destroyed battle rule cases'() {
        expect:
            processor.matchBattleRule(battle) == result

        where:
            battle                              || result
            getAllDestroyedTransformersBattle() || true
            getRegularTransformerBattle()       || false
            getOnePrimeWithTransformerBattle()  || false
    }

    def 'should get result from all destroyed battle rule processor'() {
        when:
            def result = processor.getResult()

        then:
            result
            result.allCompetitorsDestroyed
            result.numberOfFights == 0
    }
}
