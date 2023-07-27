package com.scholar.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scholar.domain.model.Teacher
import com.scholar.domain.model.TeacherNetwork


@Entity(tableName = "teachers")
data class TeacherLocal(
    @PrimaryKey
    @ColumnInfo("teacher_id")
    override val id: Int,
    @ColumnInfo(name = "full_name")
    override val fullName: String,
    override val age: Int?,
    override val profile: String?,
    override val email: String?,
    override val bio: String?,
    override val education: String?,
    override val gender: Int,
    override val phone: String?,
    override val skills: String?,
    override val qualification: String?,
    override val views: Int,
) : Teacher


fun TeacherNetwork.toLocal() =
    TeacherLocal(
        id,
        fullName,
        age,
        profile,
        email,
        bio,
        education,
        gender,
        phone,
        skills,
        qualification,
        views
    )

fun List<TeacherNetwork>.toLocal() = map(TeacherNetwork::toLocal)
