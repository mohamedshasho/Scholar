package com.scholar.data.source.local.model

import com.scholar.domain.model.TeacherSmall

data class TeacherSmallLocal(
    override val teacherId: Int?,
    override val name: String?,
    override val image: String?
) :TeacherSmall