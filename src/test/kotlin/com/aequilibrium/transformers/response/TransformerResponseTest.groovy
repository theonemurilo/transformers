package com.aequilibrium.transformers.response

import com.aequilibrium.transformers.domain.TransformerTeam
import spock.lang.Specification

import static com.aequilibrium.transformers.fixture.TransformerFixture.getTransformer

class TransformerResponseTest extends Specification {

    def 'should convert domain transformer to response'() {
        given:
            def transformer = getTransformer(1L, TransformerTeam.AUTOBOTS)

        when:
            def response = new TransformerResponse.Companion().fromDomain(transformer)

        then:
            with(response) {
                id == transformer.id
                name == transformer.name
                teamName == transformer.transformerTeam.teamName
                strength == transformer.strength
                endurance == transformer.endurance
                skill == transformer.skill
                speed == transformer.speed
                courage == transformer.courage
                intelligence == transformer.intelligence
                rank == transformer.rank
                firepower == transformer.firepower
            }
    }
}
