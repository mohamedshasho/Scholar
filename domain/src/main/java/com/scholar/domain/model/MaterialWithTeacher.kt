package com.scholar.domain.model


interface TeacherSmall {
    val teacherId: Int?
    val name: String?
    val image: String?
}

interface MaterialWithTeacher {
    val material: Material
    val totalRate: Double
    val teacher: TeacherSmall
}





