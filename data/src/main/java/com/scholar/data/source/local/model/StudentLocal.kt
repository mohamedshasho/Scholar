package com.scholar.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scholar.domain.model.Student
import com.scholar.domain.model.StudentNetwork

@Entity(tableName = "students")
data class StudentLocal(
    @PrimaryKey
    override val id: Int,
    override val fullName: String,
    override val email: String?,
    override val image: String?,
    override val wallet: String?,
) : Student

fun StudentNetwork.toLocal() = StudentLocal(id, fullName, email, image, wallet)