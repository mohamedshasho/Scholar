package com.scholar.data.source.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.scholar.domain.model.MaterialWithDetail
import com.scholar.domain.model.MaterialWithTeacher

data class MaterialWithTeacherLocal(
    @Embedded override val material: MaterialLocal,
    override val totalRate: Double,
    @Embedded override val teacher: TeacherSmallLocal,
) : MaterialWithTeacher


data class MaterialWithDetailLocal(
    @Embedded override val material: MaterialLocal,
    @Embedded override val teacher: TeacherSmallLocal?,
    @Relation(
        parentColumn = "id",
        entityColumn = "material_id"
    )
    override val rates: List<RateLocal>,
    override val classroom: String?,
    override val stage: String?,
    override val subject: String?,
    override val category: String?,
) : MaterialWithDetail