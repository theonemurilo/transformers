package com.aequilibrium.transformers.processor.battle


import spock.lang.Specification

import static com.aequilibrium.transformers.fixture.TransformersBattleFixture.getAllDestroyedTransformersBattle
import static com.aequilibrium.transformers.fixture.TransformersBattleFixture.getBothEmptyTeamsTransformersBattle
import static com.aequilibrium.transformers.fixture.TransformersBattleFixture.getJustOneAutobotTransformersBattle
import static com.aequilibrium.transformers.fixture.TransformersBattleFixture.getJustOneDecepticonTransformersBattle
import static com.aequilibrium.transformers.fixture.TransformersBattleFixture.getRegularTransformerBattle

class NoFightsBattleRuleProcessorTest extends Specification {

    private NoFightsBattleRuleProcessor processor = new NoFightsBattleRuleProcessor()

    def 'should match for all no fights battle rule cases'() {
        expect:
            processor.matchBattleRule(battle) == result

        where:
            battle                                   || result
            getBothEmptyTeamsTransformersBattle()    || true
            getJustOneAutobotTransformersBattle()    || true
            getJustOneDecepticonTransformersBattle() || true
            getRegularTransformerBattle()            || false
            getAllDestroyedTransformersBattle()      || false
    }

    def 'should get result from no fights battle rule processor'() {
        when:
            def result = processor.getResult()

        then:
            result
            result.numberOfFights == 0
            !result.allCompetitorsDestroyed
    }
}
