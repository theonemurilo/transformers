package com.aequilibrium.transformers.service

import com.aequilibrium.transformers.domain.Transformer
import com.aequilibrium.transformers.exceptions.NotFoundException
import com.aequilibrium.transformers.repository.TransformerRepository
import com.aequilibrium.transformers.request.TransformerRequest
import com.aequilibrium.transformers.response.TransformerResponse
import com.aequilibrium.transformers.response.TransformerResponse.Companion.fromDomain
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransformersService(
        private val repository: TransformerRepository
) {

    fun create(transformerRequest: TransformerRequest): TransformerResponse {
        return fromDomain(repository.save(transformerRequest.toDomain()))
    }

    fun update(id: Long, transformerRequest: TransformerRequest): TransformerResponse {
        throwErrorIfIdDoesNotExist(id)

        return fromDomain(repository.save(transformerRequest.toDomain(id)))
    }

    fun delete(id: Long) {
        throwErrorIfIdDoesNotExist(id)

        repository.deleteById(id)
    }

    @Transactional(readOnly = true)
    fun getAll(): List<TransformerResponse> {
        return repository.findAll().map { fromDomain(it) }
    }

    fun getAllByIds(ids: List<Long>): List<Transformer> = repository.findAllById(ids)

    private fun throwErrorIfIdDoesNotExist(id: Long) {
        repository.findById(id).orElseThrow {
            throw NotFoundException("transformer with id=$id not found!")
        }
    }
}
