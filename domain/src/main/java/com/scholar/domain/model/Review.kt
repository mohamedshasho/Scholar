package com.scholar.domain.model

interface Review {
    val id: Int
    val rating: Float
    val comment: String?
}


data class ReviewNetwork(
     override val id: Int,
     val id_student: Int,
     override val rating: Float,
     override val comment: String?,
) : Review