package com.aequilibrium.transformers.domain

import com.aequilibrium.transformers.exceptions.BadRequestException
import spock.lang.Specification
import spock.lang.Unroll

class TransformerTeamTest extends Specification {

    @Unroll
    def 'should get transformer team #team when string is #teamName'() {
        expect:
            new TransformerTeam.Companion().fromString(teamName) == team

        where:
            teamName      || team
            "AUTOBOTS"    || TransformerTeam.AUTOBOTS
            "DECEPTICONS" || TransformerTeam.DECEPTICONS
    }

    def 'should throw bad request when team name is invalid'() {
        when:
            new TransformerTeam.Companion().fromString("OTHER")

        then:
            thrown(BadRequestException)
    }
}
