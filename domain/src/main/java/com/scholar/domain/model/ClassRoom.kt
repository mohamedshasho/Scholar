package com.scholar.domain.model


interface ClassRoom {
    val id: Int
    val name: String
}

data class ClassRoomNetwork(
    override val id: Int,
    override val name: String,
) : ClassRoom