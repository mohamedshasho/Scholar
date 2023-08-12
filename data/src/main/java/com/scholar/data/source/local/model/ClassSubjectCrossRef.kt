package com.scholar.data.source.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "class_subject",
//    primaryKeys = ["classId", "subjectId"],
    foreignKeys = [
        ForeignKey(
            childColumns = ["classId"],
            entity = ClassRoomLocal::class,
            parentColumns = ["id"]
        ),
        ForeignKey(
            childColumns = ["subjectId"],
            entity = SubjectLocal::class,
            parentColumns = ["id"]
        ),
    ]
)
data class ClassSubjectCrossRef(
    @PrimaryKey
    val id: Int,
    val classId: Int,
    val subjectId: Int,
)