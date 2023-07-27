package com.scholar.domain.model

interface TeacherWithSubjects {
    val teacher: Teacher
    val subjects: List<Subject>
}