package com.scholar.domain.model



data class SubjectNetwork(
    override val id: Int,
    override val title: String,
    override val description: String?,
    override val price: Double?,
    override val content: String?,
) : Subject


interface Subject {
    val id: Int
    val title: String
    val description: String?
    val price: Double?
    val content: String?
}