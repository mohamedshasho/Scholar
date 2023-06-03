package com.scholar.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scholar.domain.model.Subject

@Entity(tableName = "subjects")
data class SubjectLocal(
    @PrimaryKey
    override val id: Int,
    override val title: String,
    override val description: String?,
    override val price: Double?,
    override val content: String?,
) : Subject
