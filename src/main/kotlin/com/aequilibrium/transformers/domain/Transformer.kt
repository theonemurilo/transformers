package com.aequilibrium.transformers.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "transformers")
data class Transformer(

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transformers_sq_generator")
        @SequenceGenerator(name = "transformers_sq_generator", sequenceName = "transformers_sq")
        val id: Long?,

        @Column(unique = true)
        val name: String,

        @Enumerated(EnumType.STRING)
        val transformerTeam: TransformerTeam,

        val strength: Int,
        val intelligence: Int,
        val speed: Int,
        val endurance: Int,
        val rank: Int,
        val courage: Int,
        val firepower: Int,
        val skill: Int
) {

    fun isOptimusPrime() = "optimus prime".equals(name, ignoreCase = true)

    fun isPredaking() = "predaking".equals(name, ignoreCase = true)

    fun shouldWinAutomatically() = isOptimusPrime() || isPredaking()

    fun shouldWinByStrengthAndCourage(opponent: Transformer) =
            strength - opponent.strength >= 3 && courage - opponent.courage >= 4

    fun shouldWinBySkill(opponent: Transformer) = skill - opponent.skill >= 3

    fun hasGreaterOverallThen(opponent: Transformer) = overallRating() > opponent.overallRating()

    private fun overallRating() = strength + intelligence + speed + endurance + firepower
}