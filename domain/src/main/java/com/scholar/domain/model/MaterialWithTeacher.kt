package com.scholar.domain.model


data class MaterialWithTeacher(
    val material: Material,
    val totalRate: Double,
    val teacher: SmallTeacher,
)

data class SmallTeacher(
    val id: Int?,
    val name: String?,
    val image: String?
)