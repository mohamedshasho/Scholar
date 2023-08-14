package com.scholar.domain.model


interface ClassRoom {
    val id: Int
    val name: String
}

data class ClassRoomNetwork(
    override val id: Int,
    override val name: String,
) : ClassRoom


interface ClassRoomCrossRef {
    val classroom: Int
    val stage: Int
}


data class ClassRoomCrossRefNetwork(
    override val classroom: Int,
    override val stage: Int,
) : ClassRoomCrossRef