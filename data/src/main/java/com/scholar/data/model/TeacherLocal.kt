package com.scholar.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scholar.domain.model.Teacher


@Entity(tableName = "teachers")
data class TeacherLocal(
    @PrimaryKey
    override val id: Int,
    override val name: String,
    override val age: Int?,
    override val profile: String?,
    override val bio: String?,
    override val lastSeen: String?,
    override val skills: String?,
    override val qualification: String?,
    override val views: Int,
) : Teacher
