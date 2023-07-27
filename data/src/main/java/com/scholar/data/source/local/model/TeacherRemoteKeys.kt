package com.scholar.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "teacher_remote_keys")
data class TeacherRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
)
