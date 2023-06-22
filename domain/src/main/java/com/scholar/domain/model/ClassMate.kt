package com.scholar.domain.model

interface ClassMate {
    val id: Int
    val name: String
}

data class ClassMateNetwork(
    override val id: Int,
    override val name: String,
    val stageId: Int
) : ClassMate