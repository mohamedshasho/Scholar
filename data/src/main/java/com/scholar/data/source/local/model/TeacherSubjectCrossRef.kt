package com.scholar.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "teacher_subject", primaryKeys = ["subject_id", "teacher_id"])
data class TeacherSubjectCrossRef(
    @ColumnInfo(name = "subject_id")
    val subjectId: Int,
    @ColumnInfo(name = "teacher_id")
    val teacherId: Int,
)
