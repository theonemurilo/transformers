package com.aequilibrium.transformers.service

import com.aequilibrium.transformers.domain.FightResult
import com.aequilibrium.transformers.domain.Transformer
import com.aequilibrium.transformers.domain.TransformersBattle
import com.aequilibrium.transformers.domain.TransformersNoFightBattleResult
import com.aequilibrium.transformers.processor.battle.BattleRuleProcessor
import com.aequilibrium.transformers.processor.fight.FightRuleProcessor
import com.aequilibrium.transformers.response.NoBattleResponse
import com.aequilibrium.transformers.response.TiedBattleResponse
import com.aequilibrium.transformers.response.WinLossBattleResponse
import spock.lang.Specification

import static com.aequilibrium.transformers.domain.FightStatus.TIE
import static com.aequilibrium.transformers.domain.TransformerTeam.AUTOBOTS
import static com.aequilibrium.transformers.domain.TransformerTeam.DECEPTICONS
import static com.aequilibrium.transformers.fixture.TransformerFixture.getTransformer

class BattleServiceTest extends Specification {

    private BattleService service
    private TransformersService transformersService
    private BattleRuleProcessor battleRuleProcessor
    private FightRuleProcessor fightRuleProcessor
    private List<BattleRuleProcessor> battleRuleProcessors
    private List<FightRuleProcessor> fightRuleProcessors

    def setup() {
        transformersService = Mock(TransformersService)
        battleRuleProcessor = Mock(BattleRuleProcessor)
        fightRuleProcessor = Mock(FightRuleProcessor)
        battleRuleProcessors = [battleRuleProcessor]
        fightRuleProcessors = [fightRuleProcessor]

        service = new BattleService(transformersService, battleRuleProcessors, fightRuleProcessors)
    }

    def 'should get a no fight battle response when all are destroyed'() {
        given:
            def ids = [1, 2]
            def transformers = [getTransformer(1L, AUTOBOTS), getTransformer(2L, DECEPTICONS)]

        and:
            transformersService.getAllByIds(_ as List<Long>) >> transformers

        and:
            battleRuleProcessor.matchBattleRule(_ as TransformersBattle) >> true

        and:
            battleRuleProcessor.getResult() >> new TransformersNoFightBattleResult(0, true)

        when:
            def battleResponse = service.battle(ids)

        then:
            battleResponse
            battleResponse instanceof NoBattleResponse
            battleResponse.numberOfFights == 0
            (battleResponse as NoBattleResponse).allCompetitorsDestroyed
    }

    def 'should get a no fight battle response when all were not destroyed'() {
        given:
            def ids = [1, 2]
            def transformers = [getTransformer(1L, AUTOBOTS), getTransformer(2L, DECEPTICONS)]

        and:
            transformersService.getAllByIds(_ as List<Long>) >> transformers

        and:
            battleRuleProcessor.matchBattleRule(_ as TransformersBattle) >> true

        and:
            battleRuleProcessor.getResult() >> new TransformersNoFightBattleResult(0, false)

        when:
            def battleResponse = service.battle(ids)

        then:
            battleResponse
            battleResponse instanceof NoBattleResponse
            battleResponse.numberOfFights == 0
            !(battleResponse as NoBattleResponse).allCompetitorsDestroyed
    }

    def 'should get a fight battle response when autobots win'() {
        given:
            def ids = [1, 2]
            def autobot = getTransformer(1L, AUTOBOTS)
            def decepticon = getTransformer(2L, DECEPTICONS)
            def transformers = [autobot, decepticon]

        and:
            transformersService.getAllByIds(_ as List<Long>) >> transformers

        and:
            battleRuleProcessor.matchBattleRule(_ as TransformersBattle) >> false
            fightRuleProcessor.matchFightRule(_ as Transformer, _ as Transformer) >> true

        and:
            fightRuleProcessor.getResult(_ as Transformer, _ as Transformer) >> new FightResult(autobot, decepticon)

        when:
            def battleResponse = service.battle(ids)

        then:
            battleResponse
            battleResponse instanceof WinLossBattleResponse
            battleResponse.numberOfFights == 1
            (battleResponse as WinLossBattleResponse).winningTeamName == AUTOBOTS.teamName
            (battleResponse as WinLossBattleResponse).winningTransformers.every { it.teamName == AUTOBOTS.teamName }
            (battleResponse as WinLossBattleResponse).losingTeamSurvivors.isEmpty()
    }

    def 'should get a fight battle response when decepticons win'() {
        given:
            def ids = [1, 2]
            def autobot = getTransformer(1L, AUTOBOTS)
            def decepticon = getTransformer(2L, DECEPTICONS)
            def transformers = [autobot, decepticon]

        and:
            transformersService.getAllByIds(_ as List<Long>) >> transformers

        and:
            battleRuleProcessor.matchBattleRule(_ as TransformersBattle) >> false
            fightRuleProcessor.matchFightRule(_ as Transformer, _ as Transformer) >> true

        and:
            fightRuleProcessor.getResult(_ as Transformer, _ as Transformer) >> new FightResult(decepticon, autobot)

        when:
            def battleResponse = service.battle(ids)

        then:
            battleResponse
            battleResponse instanceof WinLossBattleResponse
            battleResponse.numberOfFights == 1
            (battleResponse as WinLossBattleResponse).winningTeamName == DECEPTICONS.teamName
            (battleResponse as WinLossBattleResponse).winningTransformers.every { it.teamName == DECEPTICONS.teamName }
            (battleResponse as WinLossBattleResponse).losingTeamSurvivors.isEmpty()
    }

    def 'should get a fight battle response when battle is tied'() {
        given:
            def ids = [1, 2, 3, 4, 5, 6]
            def autobot1 = getTransformer(1L, AUTOBOTS)
            def autobot2 = getTransformer(2L, AUTOBOTS)
            def autobot3 = getTransformer(3L, AUTOBOTS)
            def decepticon1 = getTransformer(4L, DECEPTICONS)
            def decepticon2 = getTransformer(5L, DECEPTICONS)
            def decepticon3 = getTransformer(6L, DECEPTICONS)
            def transformers = [autobot1, autobot2, autobot3, decepticon1, decepticon2, decepticon3]

        and:
            transformersService.getAllByIds(ids) >> transformers

        and:
            battleRuleProcessor.matchBattleRule(_ as TransformersBattle) >> false
            fightRuleProcessor.matchFightRule(_ as Transformer, _ as Transformer) >> true

        and:
            fightRuleProcessor.getResult(autobot1, decepticon1) >> new FightResult(autobot1, decepticon1)
            fightRuleProcessor.getResult(autobot2, decepticon2) >> new FightResult(decepticon2, autobot1)
            fightRuleProcessor.getResult(autobot3, decepticon3) >> new FightResult(TIE)

        when:
            def battleResponse = service.battle(ids)

        then:
            battleResponse
            battleResponse instanceof TiedBattleResponse
            battleResponse.numberOfFights == 3
            def tiedResponse = (battleResponse as TiedBattleResponse)
            tiedResponse.autobotSurvivors.size() == 1
            tiedResponse.autobotSurvivors.first().id == autobot1.id
            tiedResponse.autobotWinners.size() == 1
            tiedResponse.autobotWinners.first().id == autobot1.id
            tiedResponse.decepticonSurvivors.size() == 1
            tiedResponse.decepticonSurvivors.first().id == decepticon2.id
            tiedResponse.decepticonWinners.size() == 1
            tiedResponse.decepticonWinners.first().id == decepticon2.id
            tiedResponse.decepticonWinners.first().id == decepticon2.id
            tiedResponse.numberOfFightsTied == 1
    }
}
