package com.aequilibrium.transformers.repository

import com.aequilibrium.transformers.domain.Transformer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransformerRepository : JpaRepository<Transformer, Long>