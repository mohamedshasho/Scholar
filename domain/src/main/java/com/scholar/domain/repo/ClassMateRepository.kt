package com.scholar.domain.repo

import com.scholar.domain.model.ClassMate
import kotlinx.coroutines.flow.Flow

interface ClassMateRepository {
    suspend fun refresh()
    suspend fun observeClassesMate(): Flow<List<ClassMate>>
}