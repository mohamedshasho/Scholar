package com.scholar.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "story_remote_keys")
data class StoryRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
)