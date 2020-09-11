package com.aequilibrium.transformers.controller


import com.aequilibrium.transformers.request.TransformerRequest
import com.aequilibrium.transformers.service.BattleService
import com.aequilibrium.transformers.service.TransformersService
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static com.aequilibrium.transformers.fixture.TransformerRequestFixture.getTransformerRequest
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(TransformersController)
class TransformersControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private ObjectMapper mapper

    @SpringBean
    private TransformersService transformersService = Mock(TransformersService)

    @SpringBean
    private BattleService battleService = Mock(BattleService)

    def 'should call create method'() {
        given:
            def transformerRequest = getTransformerRequest()
            def payload = mapper.writeValueAsString(transformerRequest)

        when:
            def resultActions = mvc.perform(post("/transformers")
                    .content(payload)
                    .contentType(APPLICATION_JSON))

        then:
            resultActions.andExpect(status().isCreated())

        and:
            1 * transformersService.create(_ as TransformerRequest)
    }

    def 'should call update method'() {
        given:
            def transformerRequest = getTransformerRequest()
            def payload = mapper.writeValueAsString(transformerRequest)

        when:
            def resultActions = mvc.perform(put("/transformers/1")
                    .content(payload)
                    .contentType(APPLICATION_JSON))

        then:
            resultActions.andExpect(status().isOk())

        and:
            1 * transformersService.update(1, _ as TransformerRequest)
    }

    def 'should call get all method'() {
        when:
            def resultActions = mvc.perform(get("/transformers"))

        then:
            resultActions.andExpect(status().isOk())

        and:
            1 * transformersService.getAll()
    }

    def 'should call delete method'() {
        when:
            def resultActions = mvc.perform(delete("/transformers/1"))

        then:
            resultActions.andExpect(status().isNoContent())

        and:
            1 * transformersService.delete(1L)
    }

    def 'should call get battle method'() {
        given:
            def payload = mapper.writeValueAsString([1, 2, 3, 4, 5, 6])

        when:
            def resultActions = mvc.perform(post("/transformers/battles")
                    .contentType(APPLICATION_JSON)
                    .content(payload))

        then:
            resultActions.andExpect(status().isOk())

        and:
            1 * battleService.battle(_ as List)
    }
}
