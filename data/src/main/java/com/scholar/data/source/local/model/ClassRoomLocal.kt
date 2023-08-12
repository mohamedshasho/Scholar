package com.scholar.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scholar.domain.model.ClassRoom
import com.scholar.domain.model.ClassRoomNetwork

@Entity(
    tableName = "classes"
)
data class ClassRoomLocal(
    @PrimaryKey
    override val id: Int,
    override val name: String,
) : ClassRoom

fun ClassRoomNetwork.toLocal() = ClassRoomLocal(id, name = name)
fun List<ClassRoomNetwork>.toLocal() = map(ClassRoomNetwork::toLocal)
