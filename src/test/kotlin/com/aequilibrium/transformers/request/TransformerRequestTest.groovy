package com.aequilibrium.transformers.request

import com.aequilibrium.transformers.domain.TransformerTeam
import spock.lang.Specification

import static com.aequilibrium.transformers.fixture.TransformerRequestFixture.getTransformerRequest

class TransformerRequestTest extends Specification {

    def 'should convert request to domain'() {
        given:
            def transformerRequest = getTransformerRequest()

        when:
            def transformer = transformerRequest.toDomain()

        then:
            with(transformer) {
                id == null
                name == transformerRequest.name
                transformerTeam == new TransformerTeam.Companion().fromString(transformerRequest.teamName)
                strength == transformerRequest.strength
                strength == transformerRequest.strength
                endurance == transformerRequest.endurance
                skill == transformerRequest.skill
                speed == transformerRequest.speed
                courage == transformerRequest.courage
                intelligence == transformerRequest.intelligence
                rank == transformerRequest.rank
                firepower == transformerRequest.firepower
            }
    }

    def 'should convert request to domain passing id'() {
        given:
            def transformerRequest = getTransformerRequest()

        when:
            def transformer = transformerRequest.toDomain(1L)

        then:
            with(transformer) {
                id == 1L
                name == transformerRequest.name
                transformerTeam == new TransformerTeam.Companion().fromString(transformerRequest.teamName)
                strength == transformerRequest.strength
                endurance == transformerRequest.endurance
                skill == transformerRequest.skill
                speed == transformerRequest.speed
                courage == transformerRequest.courage
                intelligence == transformerRequest.intelligence
                rank == transformerRequest.rank
                firepower == transformerRequest.firepower
            }
    }
}
