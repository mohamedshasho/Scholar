package com.scholar.domain.repo

import androidx.paging.PagingData
import com.scholar.domain.model.MessageResponse
import com.scholar.domain.model.Resource
import com.scholar.domain.model.Teacher
import com.scholar.domain.model.TeacherWithSubjects
import kotlinx.coroutines.flow.Flow

interface TeacherRepository {
    suspend fun observeTeachers(): Flow<List<Teacher>>

    fun getTeachersPagination(): Flow<PagingData<TeacherWithSubjects>>

    suspend fun getTeacherByIdFromLocal(id: Int): Teacher?

    fun searchForTeachers(input: String): Flow<PagingData<TeacherWithSubjects>>

    suspend fun contact(
        name: String,
        email: String,
        phone: String,
        subject: String
    ): Resource<MessageResponse>
}