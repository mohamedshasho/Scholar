package com.scholar.data.source.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.scholar.domain.model.ClassMate
import com.scholar.domain.model.ClassMateNetwork

@Entity(
    tableName = "classes",
    foreignKeys = [
        ForeignKey(
            childColumns = ["stageId"],
            entity = StageLocal::class,
            parentColumns = ["id"],
            onDelete = CASCADE
        ),
    ]
)
data class ClassMateLocal(
    @PrimaryKey
    override val id: Int,
    val stageId: Int,
    override val name: String,
) : ClassMate

fun ClassMateNetwork.toLocal() = ClassMateLocal(id, name = name, stageId = stageId)
fun List<ClassMateNetwork>.toLocal() = map(ClassMateNetwork::toLocal)
