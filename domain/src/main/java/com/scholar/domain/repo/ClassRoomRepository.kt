package com.scholar.domain.repo

import com.scholar.domain.model.ClassRoom
import kotlinx.coroutines.flow.Flow

interface ClassRoomRepository {
    suspend fun refresh()
    suspend fun observeClassesRooms(): Flow<List<ClassRoom>>
}