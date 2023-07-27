package com.scholar.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scholar.domain.model.CategoryNetwork
import com.scholar.domain.model.Subject
import com.scholar.domain.model.SubjectNetwork

@Entity(tableName = "subjects")
data class SubjectLocal(
    @PrimaryKey
    @ColumnInfo("subject_id")
    override val id: Int,
    override val name: String,
) : Subject


fun SubjectNetwork.toLocal() = SubjectLocal(id, name)

fun List<SubjectNetwork>.toLocal() = map(SubjectNetwork::toLocal)