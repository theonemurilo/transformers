package com.aequilibrium.transformers.domain

import com.aequilibrium.transformers.domain.FightStatus.WIN_LOSS

class FightResult(
        val fightStatus: FightStatus,
        val winner: Transformer?,
        val loser: Transformer?
) {
    constructor(fightStatus: FightStatus) : this(fightStatus, null, null)
    constructor(winner: Transformer, loser: Transformer) : this(WIN_LOSS, winner, loser)
}
