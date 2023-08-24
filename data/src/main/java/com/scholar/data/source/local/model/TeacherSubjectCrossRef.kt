package com.scholar.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


@Entity(
    tableName = "teacher_subject", primaryKeys = ["subject_id", "teacher_id"],
    foreignKeys = [
        ForeignKey(
            entity = SubjectLocal::class,
            parentColumns = ["subject_id"],
            childColumns = ["subject_id"]
        ),
        ForeignKey(
            entity = TeacherLocal::class,
            parentColumns = ["teacher_id"],
            childColumns = ["teacher_id"]
        ),
    ],
    indices = [Index("teacher_id")]
)
data class TeacherSubjectCrossRef(
    @ColumnInfo(name = "subject_id")
    val subjectId: Int,
    @ColumnInfo(name = "teacher_id")
    val teacherId: Int,
)
