package com.scholar.data.source.local.model

import androidx.room.Embedded
import com.scholar.domain.model.MaterialWithTeacher
import com.scholar.domain.model.TeacherSmall

data class MaterialWithTeacherLocal(
    @Embedded override val material: MaterialLocal,
    override val totalRate: Double,
    override val teacher: TeacherSmall,
    ) : MaterialWithTeacher

