package com.scholar.domain.repo

import com.scholar.domain.model.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {
    suspend fun getSubjects(): Flow<List<Subject>>
    suspend fun refresh()
}