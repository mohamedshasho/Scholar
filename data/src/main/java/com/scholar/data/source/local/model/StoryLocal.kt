package com.scholar.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scholar.domain.model.Story
import com.scholar.domain.model.StoryNetwork

@Entity(tableName = "stories")
data class StoryLocal(
    @PrimaryKey
    override val id: Int,
    @ColumnInfo(name = "student_id")
    val studentId: Int,
    override val title: String,
    override val description: String?,
    override val image: String?,
    @ColumnInfo(name = "date_publication")
    override val datePublication: String?,

    ) : Story

fun StoryNetwork.toLocal() = StoryLocal(
    id, student.id, title, description, image, datePublication,
)

fun List<StoryNetwork>.toLocal() = map(StoryNetwork::toLocal)