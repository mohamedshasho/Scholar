package com.scholar.data.repo

import com.scholar.data.source.local.dao.StudentDao
import com.scholar.domain.repo.StudentRepository

class StudentRepositoryImp(
    private val studentDao: StudentDao,
) :StudentRepository{
    override suspend fun getStudentName(id: Int): String? {
        return studentDao.getStudentName(id)
    }

    override  fun getStudent(id: Int) = studentDao.getStudent(id)
}