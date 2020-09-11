package com.aequilibrium.transformers.fixture

import com.aequilibrium.transformers.domain.FightResult

import static com.aequilibrium.transformers.domain.FightStatus.TIE
import static com.aequilibrium.transformers.domain.TransformerTeam.AUTOBOTS
import static com.aequilibrium.transformers.domain.TransformerTeam.DECEPTICONS
import static com.aequilibrium.transformers.fixture.TransformerFixture.getTransformer

class FightResultFixture {

    private FightResultFixture() {
    }

    static FightResult getAutobotWinnerFightResult() {
        new FightResult(getTransformer(1L, AUTOBOTS), getTransformer(2L, DECEPTICONS))
    }

    static FightResult getDecepticonWinnerFightResult() {
        new FightResult(getTransformer(2L, DECEPTICONS), getTransformer(1L, AUTOBOTS))
    }

    static FightResult getTiedFightResult() {
        new FightResult(TIE)
    }
}
