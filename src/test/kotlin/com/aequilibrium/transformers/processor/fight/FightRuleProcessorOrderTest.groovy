package com.aequilibrium.transformers.processor.fight


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class FightRuleProcessorOrderTest extends Specification {

    @Autowired
    private List<FightRuleProcessor> fightRuleProcessors

    def 'should get a list of fight rule processors in correct order'() {
        when:
            def first = fightRuleProcessors.first()
            def second = fightRuleProcessors.get(1)
            def third = fightRuleProcessors.get(2)
            def fourth = fightRuleProcessors.get(3)

        then:
            first instanceof AutomaticallyWinLossFightRuleProcessor
            second instanceof CourageAndStrengthFightRuleProcessor
            third instanceof SkillFightRuleProcessor
            fourth instanceof OverallRatingFightRuleProcessor
    }
}
