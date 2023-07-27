package com.scholar.data.source.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.scholar.domain.model.TeacherWithSubjects

data class TeacherWithSubjectsLocal(
    @Embedded override val teacher: TeacherLocal,
    @Relation(
        parentColumn = "teacher_id",
        entityColumn = "subject_id",
        associateBy = Junction(TeacherSubjectCrossRef::class)
    )
    override val subjects: List<SubjectLocal>,
) : TeacherWithSubjects
