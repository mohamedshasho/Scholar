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
    override val birth: String?,
    override val image: String?,
    override val email: String?,
    override val bio: String?,
    override val education: String?,
    override val phone: String?,
    override val qualifications: String?,
) : Teacher



fun TeacherLocal.toSmall() =
    TeacherSmallLocal(
        id,
        fullName,
        image = image,
    )

fun TeacherNetwork.toLocal() =
    TeacherLocal(
        id,
        fullName,
        birth = birth,
        image = image,
        email = email,
        bio = bio,
        education = education,
        phone = phone,
        qualifications=qualifications
    )

fun List<TeacherNetwork>.toLocal() = map(TeacherNetwork::toLocal)
