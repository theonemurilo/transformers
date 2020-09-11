package com.aequilibrium.transformers.fixture

import com.aequilibrium.transformers.domain.Transformer
import com.aequilibrium.transformers.domain.TransformerTeam

import static com.aequilibrium.transformers.domain.TransformerTeam.AUTOBOTS
import static com.aequilibrium.transformers.domain.TransformerTeam.DECEPTICONS

class TransformerFixture {

    private TransformerFixture() {
    }

    static Transformer getTransformer() {
        new Transformer(
                1L,
                "Optimus Prime",
                AUTOBOTS,
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8
        )
    }

    static Transformer getPrime() {
        new Transformer(
                1L,
                "Optimus Prime",
                AUTOBOTS,
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8
        )
    }

    static Transformer getPredakin() {
        new Transformer(
                2L,
                "Predaking",
                DECEPTICONS,
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8
        )
    }

    static Transformer getTransformer(Long id, TransformerTeam transformerTeam) {
        new Transformer(
                id,
                'T2',
                transformerTeam,
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8
        )
    }

    static Transformer getTransformer(Integer strength, Integer courage, TransformerTeam transformerTeam) {
        new Transformer(
                1L,
                'T2',
                transformerTeam,
                strength,
                2,
                3,
                4,
                5,
                courage,
                7,
                8
        )
    }

    static Transformer getTransformer(Integer skill, TransformerTeam transformerTeam) {
        new Transformer(
                1L,
                'T2',
                transformerTeam,
                1,
                2,
                3,
                4,
                5,
                1,
                7,
                skill
        )
    }
}
