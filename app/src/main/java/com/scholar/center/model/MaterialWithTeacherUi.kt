package com.scholar.center.model

import com.scholar.domain.model.Material
import com.scholar.domain.model.MaterialWithTeacher
import com.scholar.domain.model.TeacherSmall

class MaterialWithTeacherUi(
    override val material: Material,
    override val teacher: TeacherSmall,
    override val totalRate: Double,
    var isBought: Boolean = false
) : MaterialWithTeacher