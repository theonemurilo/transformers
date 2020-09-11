package com.aequilibrium.transformers.service

import com.aequilibrium.transformers.domain.Transformer
import com.aequilibrium.transformers.exceptions.NotFoundException
import com.aequilibrium.transformers.repository.TransformerRepository
import spock.lang.Specification

import static com.aequilibrium.transformers.domain.TransformerTeam.AUTOBOTS
import static com.aequilibrium.transformers.domain.TransformerTeam.DECEPTICONS
import static com.aequilibrium.transformers.fixture.TransformerFixture.getTransformer
import static com.aequilibrium.transformers.fixture.TransformerRequestFixture.getTransformerRequest
import static java.util.Optional.empty
import static java.util.Optional.of

class TransformersServiceTest extends Specification {

    private TransformersService service
    private TransformerRepository repository

    def setup() {
        repository = Mock(TransformerRepository)
        service = new TransformersService(repository)
    }

    def 'should save a new transformer'() {
        given:
            def transformerId = 1L
            def transformerRequest = getTransformerRequest()
            def transformer = transformerRequest.toDomain(transformerId)

        and:
            repository.save(_ as Transformer) >> transformer

        when:
            def response = service.create(transformerRequest)

        then:
            with(response) {
                id == transformerId
                name == transformer.name
                teamName == transformer.transformerTeam.teamName
                strength == transformer.strength
                intelligence == transformer.intelligence
                speed == transformer.speed
                rank == transformer.rank
                skill == transformer.skill
                endurance == transformer.endurance
                courage == transformer.courage
                firepower == transformer.firepower
            }
    }

    def 'should update an existent transformer'() {
        given:
            def transformerId = 1L
            def transformerRequest = getTransformerRequest('new Transformer')
            def transformer = transformerRequest.toDomain(transformerId)

        and:
            repository.findById(transformerId) >> of(transformer)

        and:
            repository.save(_ as Transformer) >> transformer

        when:
            def response = service.update(transformerId, transformerRequest)

        then:
            with(response) {
                id == transformerId
                name == transformer.name
                teamName == transformer.transformerTeam.teamName
                strength == transformer.strength
                intelligence == transformer.intelligence
                speed == transformer.speed
                rank == transformer.rank
                skill == transformer.skill
                endurance == transformer.endurance
                courage == transformer.courage
                firepower == transformer.firepower
            }
    }

    def 'should throw not found when trying to update a transformer with not existent id'() {
        given:
            def transformerId = 1L
            def transformerRequest = getTransformerRequest('new Transformer')

        and:
            repository.findById(transformerId) >> empty()

        when:
            service.update(transformerId, transformerRequest)

        then:
            thrown(NotFoundException)
    }

    def 'should delete a transformer'() {
        given:
            def transformerId = 1L
            def transformerRequest = getTransformerRequest('new Transformer')
            def transformer = transformerRequest.toDomain(transformerId)

        and:
            repository.findById(transformerId) >> of(transformer)

        when:
            service.delete(transformerId)

        then:
            1 * repository.deleteById(transformerId)
    }

    def 'should throw not found when trying to delete a transformer with id not existent'() {
        given:
            def transformerId = 1L

        and:
            repository.findById(transformerId) >> empty()

        when:
            service.delete(transformerId)

        then:
            thrown(NotFoundException)
    }

    def 'should list all transformers'() {
        given:
            def transformers = [getTransformer(1L, AUTOBOTS),
                                getTransformer(2L, DECEPTICONS)]

        and:
            repository.findAll() >> transformers

        when:
            def list = service.getAll()

        then:
            list
            list.size() == 2
    }

    def 'should list all by ids transformers'() {
        given:
            def transformers = [getTransformer(1L, AUTOBOTS),
                                getTransformer(2L, DECEPTICONS)]

        and:
            repository.findAllById(_ as Iterable) >> transformers

        when:
            def list = service.getAllByIds(transformer.collect { it.id })

        then:
            list
            list.size() == 2
    }
}
