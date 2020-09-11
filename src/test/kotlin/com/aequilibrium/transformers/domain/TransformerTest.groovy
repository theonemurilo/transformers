package com.aequilibrium.transformers.domain

import com.aequilibrium.transformers.fixture.TransformerFixture
import spock.lang.Specification

import static com.aequilibrium.transformers.domain.TransformerTeam.AUTOBOTS
import static com.aequilibrium.transformers.domain.TransformerTeam.DECEPTICONS
import static com.aequilibrium.transformers.fixture.TransformerFixture.getPredakin
import static com.aequilibrium.transformers.fixture.TransformerFixture.getPrime

class TransformerTest extends Specification {

    def 'should return correct result when transformer is optimus prime'() {
        expect:
            transformer.isOptimusPrime() == result

        where:
            transformer                                     || result
            getPrime()                                      || true
            getPredakin()                                   || false
            TransformerFixture.getTransformer(1L, AUTOBOTS) || false
    }

    def 'should return correct result when transformer is predaking'() {
        expect:
            transformer.isPredaking() == result

        where:
            transformer                                     || result
            getPredakin()                                   || true
            getPrime()                                      || false
            TransformerFixture.getTransformer(1L, AUTOBOTS) || false
    }

    def 'should win automatically when transformer is prime or predaking'() {
        expect:
            transformer.shouldWinAutomatically() == result

        where:
            transformer                                     || result
            getPredakin()                                   || true
            getPrime()                                      || true
            TransformerFixture.getTransformer(1L, AUTOBOTS) || false
    }

    def 'should win by strength and courage'() {
        expect:
            transformer.shouldWinByStrengthAndCourage(oppponent) == result

        where:
            transformer                                         | oppponent                                              || result
            TransformerFixture.getTransformer(10, 10, AUTOBOTS) | TransformerFixture.getTransformer(7, 6, DECEPTICONS)   || true
            TransformerFixture.getTransformer(7, 6, AUTOBOTS)   | TransformerFixture.getTransformer(10, 10, DECEPTICONS) || false
            TransformerFixture.getTransformer(7, 6, AUTOBOTS)   | TransformerFixture.getTransformer(7, 6, DECEPTICONS)   || false
    }

    def 'should win by skill'() {
        expect:
            transformer.shouldWinBySkill(oppponent) == result

        where:
            transformer                                     | oppponent                                          || result
            TransformerFixture.getTransformer(10, AUTOBOTS) | TransformerFixture.getTransformer(7, DECEPTICONS)  || true
            TransformerFixture.getTransformer(7, AUTOBOTS)  | TransformerFixture.getTransformer(10, DECEPTICONS) || false
            TransformerFixture.getTransformer(7, AUTOBOTS)  | TransformerFixture.getTransformer(7, DECEPTICONS)  || false
    }

    def 'should have greater overall rating'() {
        expect:
            transformer.hasGreaterOverallThen(oppponent) == result

        where:
            transformer                                         | oppponent                                              || result
            TransformerFixture.getTransformer(10, 10, AUTOBOTS) | TransformerFixture.getTransformer(9, 10, DECEPTICONS)  || true
            TransformerFixture.getTransformer(9, 10, AUTOBOTS)  | TransformerFixture.getTransformer(10, 10, DECEPTICONS) || false
            TransformerFixture.getTransformer(7, 6, AUTOBOTS)   | TransformerFixture.getTransformer(7, 6, DECEPTICONS)   || false
    }
}
