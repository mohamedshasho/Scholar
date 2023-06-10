package com.scholar.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scholar.domain.model.Story
import com.scholar.domain.model.StoryNetwork

@Entity(tableName = "stories")
data class StoryLocal(
    @PrimaryKey
    override val id: Int,
    override val title: String,
    override val description: String?,
    override val image: String?,
) : Story

fun StoryNetwork.toLocal() = StoryLocal(id, title, description, image)
fun List<StoryNetwork>.toLocal() = map(StoryNetwork::toLocal)