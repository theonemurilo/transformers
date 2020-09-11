package com.aequilibrium.transformers.processor.battle


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class BattleRuleProcessorsOrderTest extends Specification {

    @Autowired
    private List<BattleRuleProcessor> battleRuleProcessors

    def 'should list of battle rule processors be injected in correct order'() {
        when:
            def first = battleRuleProcessors.first()
            def second = battleRuleProcessors.get(1)

        then:
            first instanceof NoFightsBattleRuleProcessor
            second instanceof AllDestroyedBattleRuleProcessor
    }
}
