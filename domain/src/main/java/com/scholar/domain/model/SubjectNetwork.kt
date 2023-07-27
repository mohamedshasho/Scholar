package com.scholar.domain.model


data class SubjectNetwork(
    override val id: Int,
    override val name: String,
) : Subject


interface Subject {
    val id: Int
    val name: String
}