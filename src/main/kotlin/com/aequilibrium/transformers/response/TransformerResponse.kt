package com.aequilibrium.transformers.response

import com.aequilibrium.transformers.domain.Transformer


data class TransformerResponse(
        val id: Long,
        val name: String,
        val teamName: String,
        val strength: Int,
        val intelligence: Int,
        val speed: Int,
        val endurance: Int,
        val rank: Int,
        val courage: Int,
        val firepower: Int,
        val skill: Int
) {
    companion object {
        fun fromDomain(transformer: Transformer) =
                TransformerResponse(
                        transformer.id!!,
                        transformer.name,
                        transformer.transformerTeam.teamName,
                        transformer.strength,
                        transformer.intelligence,
                        transformer.speed,
                        transformer.endurance,
                        transformer.rank,
                        transformer.courage,
                        transformer.firepower,
                        transformer.skill)
    }
}
