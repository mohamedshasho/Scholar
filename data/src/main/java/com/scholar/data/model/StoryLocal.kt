package com.scholar.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scholar.domain.model.Story

@Entity(tableName = "stories")
data class StoryLocal(
    @PrimaryKey
    override val id: Int,
    override val title: String,
    override val description: String?,
    override val image: String?,
) : Story
