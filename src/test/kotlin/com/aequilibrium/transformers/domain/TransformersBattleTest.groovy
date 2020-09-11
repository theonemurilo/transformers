package com.aequilibrium.transformers.domain


import spock.lang.Specification

import static com.aequilibrium.transformers.domain.TransformerTeam.AUTOBOTS
import static com.aequilibrium.transformers.domain.TransformerTeam.DECEPTICONS
import static com.aequilibrium.transformers.fixture.TransformerFixture.getPrime
import static com.aequilibrium.transformers.fixture.TransformerFixture.getTransformer
import static com.aequilibrium.transformers.fixture.TransformerFixture.getPredakin

class TransformersBattleTest extends Specification {

    def 'should return number of fights'() {
        expect:
            new TransformersBattle(autobots, decepticons).numberOfFights() == numberOfFights

        where:
            autobots                 | decepticons                    || numberOfFights
            []                       | [getPredakin()]                || 0
            [getPrime()]             | []                             || 0
            [getPrime()]             | [getPredakin()]                || 1
            [getPrime(), getPrime()] | [getPredakin(), getPredakin()] || 2
            [getPrime(), getPrime()] | [getPredakin()]                || 1
            [getPrime()]             | [getPredakin(), getPredakin()] || 1
            []                       | []                             || 0
    }

    def 'should all transformers by destroyed'() {
        expect:
            new TransformersBattle(autobots, decepticons).shouldAllBeDestroyed() == shouldAllBeDestroyed

        where:
            autobots                                   | decepticons                                      || shouldAllBeDestroyed
            [getPrime()]                               | [getPredakin()]                                  || true
            [getPrime(), getTransformer(1L, AUTOBOTS)] | [getPredakin(), getTransformer(2L, DECEPTICONS)] || true
            [getPrime(), getTransformer(1L, AUTOBOTS)] | [getTransformer(2L, DECEPTICONS), getPredakin()] || false
            [getPrime()]                               | []                                               || false
            []                                         | [getPredakin()]                                  || false
            [getTransformer(1L, AUTOBOTS)]             | [getPredakin()]                                  || false
            [getPrime()]                               | [getTransformer(2L, DECEPTICONS)]                || false
            []                                         | []                                               || false
    }
}
