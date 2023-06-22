package com.scholar.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stages")
data class StageLocal(
    @PrimaryKey
    val id: Int,
    val name: String,
)
