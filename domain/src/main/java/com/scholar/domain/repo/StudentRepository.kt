package com.scholar.domain.repo

import com.scholar.domain.model.Student
import kotlinx.coroutines.flow.Flow


interface StudentRepository {

    suspend fun getStudentName(id: Int): String?
    fun getStudent(id: Int): Flow<Student?>
}