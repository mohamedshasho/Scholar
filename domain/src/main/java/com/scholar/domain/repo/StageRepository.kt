package com.scholar.domain.repo

import com.scholar.domain.model.Stage
import kotlinx.coroutines.flow.Flow

interface StageRepository {
    suspend fun refresh()
    suspend fun observeStages(): Flow<List<Stage>>
}

