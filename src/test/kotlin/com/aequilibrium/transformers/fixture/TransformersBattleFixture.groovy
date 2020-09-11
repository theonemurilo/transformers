package com.aequilibrium.transformers.fixture


import com.aequilibrium.transformers.domain.TransformersBattle

import static com.aequilibrium.transformers.domain.TransformerTeam.AUTOBOTS
import static com.aequilibrium.transformers.domain.TransformerTeam.DECEPTICONS
import static com.aequilibrium.transformers.fixture.TransformerFixture.getPredakin
import static com.aequilibrium.transformers.fixture.TransformerFixture.getPrime
import static com.aequilibrium.transformers.fixture.TransformerFixture.getTransformer

class TransformersBattleFixture {

    private TransformersBattleFixture() {
    }

    static TransformersBattle getAllDestroyedTransformersBattle() {
        new TransformersBattle([getPrime()], [getPredakin()])
    }

    static TransformersBattle getRegularTransformerBattle() {
        new TransformersBattle([getTransformer(1L, AUTOBOTS)], [getTransformer(2L, DECEPTICONS)])
    }

    static TransformersBattle getOnePrimeWithTransformerBattle() {
        new TransformersBattle([getPrime()], [getTransformer(2L, DECEPTICONS)])
    }

    static TransformersBattle getBothEmptyTeamsTransformersBattle() {
        new TransformersBattle([], [])
    }

    static TransformersBattle getJustOneAutobotTransformersBattle() {
        new TransformersBattle([getPrime()], [])
    }

    static TransformersBattle getJustOneDecepticonTransformersBattle() {
        new TransformersBattle([], [getPredakin()])
    }
}
