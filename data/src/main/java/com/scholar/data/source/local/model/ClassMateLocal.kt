package com.scholar.data.source.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "classes",
    foreignKeys = [
        ForeignKey(
            childColumns = ["stageId"],
            entity = StageLocal::class,
            parentColumns = ["id"]
        ),
    ]
)
data class ClassMateLocal(
    @PrimaryKey
    val id: Int,
    val stageId: Int,
    val name: String,
)
