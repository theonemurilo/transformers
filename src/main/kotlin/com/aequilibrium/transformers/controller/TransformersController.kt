package com.aequilibrium.transformers.controller

import com.aequilibrium.transformers.request.TransformerRequest
import com.aequilibrium.transformers.service.BattleService
import com.aequilibrium.transformers.service.TransformersService
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Size

@RestController
@RequestMapping("/transformers")
@Validated
class TransformersController(
        private val transformersService: TransformersService,
        private val battleService: BattleService
) {

    @ApiOperation(value = "Creates one Transformer")
    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@RequestBody @Valid transformerRequest: TransformerRequest) =
            transformersService.create(transformerRequest)

    @ApiOperation(value = "Updates a Transformer")
    @PutMapping("/{id}")
    fun update(@RequestBody @Valid transformerRequest: TransformerRequest, @PathVariable("id") id: Long) =
            transformersService.update(id, transformerRequest)

    @ApiOperation(value = "Removes a Transformer")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    fun delete(@PathVariable("id") id: Long) = transformersService.delete(id)

    @ApiOperation(value = "Returns all Transformers")
    @GetMapping
    fun list() = transformersService.getAll()

    @ApiOperation(value = "Starts a Transformer Battle")
    @PostMapping("/battles")
    fun battle(@RequestBody @Size(min = 2) ids: List<Long>) = battleService.battle(ids)
}
