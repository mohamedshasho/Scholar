package com.scholar.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class SubjectLocal(
    @PrimaryKey
    val id: Int,
    val name: String,
)
