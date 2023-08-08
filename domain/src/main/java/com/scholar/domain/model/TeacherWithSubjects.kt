package com.scholar.domain.model

interface TeacherWithSubjects {
    val teacher: Teacher
    val subjects: List<Subject>
}


data class TeacherWithSubjectsNetwork(
    override val teacher: Teacher,
    override val subjects: List<Subject>,
) : TeacherWithSubjects