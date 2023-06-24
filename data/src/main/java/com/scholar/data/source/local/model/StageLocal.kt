package com.scholar.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scholar.domain.model.Stage
import com.scholar.domain.model.StageNetwork
import com.scholar.domain.model.StoryNetwork

@Entity(tableName = "stages")
data class StageLocal(
    @PrimaryKey
    override val id: Int,
    override val name: String,
) : Stage

fun StageNetwork.toLocal() = StageLocal(id, name)
fun List<StageNetwork>.toLocal() = map(StageNetwork::toLocal)