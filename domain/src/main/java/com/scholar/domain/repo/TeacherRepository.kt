package com.scholar.domain.repo

import com.scholar.domain.model.Teacher
import kotlinx.coroutines.flow.Flow

interface TeacherRepository {


    suspend fun observeTeachers(): Flow<List<Teacher>>

    suspend fun refresh()

    suspend fun getTeacherByIdFromLocal(id: Int): Teacher?
}