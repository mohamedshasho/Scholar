package com.scholar.domain.repo

import androidx.paging.PagingData
import com.scholar.domain.model.Teacher
import com.scholar.domain.model.TeacherWithSubjects
import kotlinx.coroutines.flow.Flow

interface TeacherRepository {


    suspend fun observeTeachers(): Flow<List<Teacher>>


    fun getTeachersPagination(): Flow<PagingData<TeacherWithSubjects>>
//    fun getTeacherWithSubjects(): Flow<TeacherWithSubject>


    suspend fun refresh()

    suspend fun getTeacherByIdFromLocal(id: Int): Teacher?
}