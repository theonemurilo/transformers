package com.aequilibrium.transformers.response

data class ErrorResponse(
        val message: String,
        val code: Int
)