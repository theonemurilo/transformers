package com.aequilibrium.transformers.domain

import com.aequilibrium.transformers.exceptions.BadRequestException

enum class TransformerTeam(
        val teamName: String
) {

    AUTOBOTS("Autobots"),
    DECEPTICONS("Decepticons");

    fun isAutobot() = AUTOBOTS == this
    fun isDecepticon() = DECEPTICONS == this

    companion object {
        fun fromString(name: String) = values().find { it.toString() == name }
                ?: throw BadRequestException("teamName=$name informed is invalid!")
    }
}