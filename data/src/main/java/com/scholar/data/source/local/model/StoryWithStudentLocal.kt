package com.scholar.data.source.local.model


import androidx.room.Embedded
import androidx.room.Relation
import com.scholar.domain.model.StoryWithStudent

data class StoryWithStudentLocal(
    @Embedded override val story: StoryLocal,
    @Relation(
        parentColumn = "student_id",
        entityColumn = "id"
    )
    override val student: StudentLocal,
) : StoryWithStudent



