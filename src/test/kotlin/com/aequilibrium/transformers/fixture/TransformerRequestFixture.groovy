package com.aequilibrium.transformers.fixture

import com.aequilibrium.transformers.request.TransformerRequest

class TransformerRequestFixture {

    private TransformerRequestFixture() {
    }

    static TransformerRequest getTransformerRequest() {
        new TransformerRequest(
                "Optimus Prime",
                "AUTOBOTS",
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

    static TransformerRequest getTransformerRequest(String name) {
        new TransformerRequest(
                name,
                "AUTOBOTS",
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
}
