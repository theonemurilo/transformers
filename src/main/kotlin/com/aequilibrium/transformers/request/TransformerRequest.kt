package com.aequilibrium.transformers.request

import com.aequilibrium.transformers.domain.Transformer
import com.aequilibrium.transformers.domain.TransformerTeam.Companion.fromString
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class TransformerRequest(
        @field:NotBlank(message = "name is required")
        val name: String?,

        @field:Pattern(regexp = "AUTOBOTS|DECEPTICONS", message = "teamName should be AUTOBOTS or DECEPTICONS")
        val teamName: String?,

        @field:Min(1, message = "strength cannot be lower than 1")
        @field:Max(10, message = "strength cannot be greater than 10")
        val strength: Int?,

        @field:Min(1, message = "intelligence cannot be lower than 1")
        @field:Max(10, message = "intelligence cannot be greater than 10")
        val intelligence: Int?,

        @field:Min(1, message = "speed cannot be lower than 1")
        @field:Max(10, message = "speed cannot be greater than 10")
        val speed: Int?,

        @field:Min(1, message = "endurance cannot be lower than 1")
        @field:Max(10, message = "endurance cannot be greater than 10")
        val endurance: Int?,

        @field:Min(1, message = "rank cannot be lower than 1")
        @field:Max(10, message = "rank cannot be greater than 10")
        val rank: Int?,

        @field:Min(1, message = "courage cannot be lower than 1")
        @field:Max(10, message = "courage cannot be greater than 10")
        val courage: Int?,

        @field:Min(1, message = "firepower cannot be lower than 1")
        @field:Max(10, message = "firepower cannot be greater than 10")
        val firepower: Int?,

        @field:Min(1, message = "skill cannot be lower than 1")
        @field:Max(10, message = "skill cannot be greater than 10")
        val skill: Int?
) {

    fun toDomain(id: Long?): Transformer {
        return Transformer(
                id,
                name!!,
                fromString(teamName!!),
                strength!!,
                intelligence!!,
                speed!!,
                endurance!!,
                rank!!,
                courage!!,
                firepower!!,
                skill!!
        )
    }

    fun toDomain(): Transformer {
        return toDomain(null)
    }
}
