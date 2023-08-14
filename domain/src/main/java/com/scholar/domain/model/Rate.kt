package com.scholar.domain.model

import com.google.gson.annotations.SerializedName

interface Rate {
    val rate: Int
    val comment: String?
}


data class RateNetwork(
    @SerializedName("student_id")
    val studentId: Int,
    @SerializedName("material_id")
    val materialId: Int,
    override val rate: Int,
    override val comment: String?,
) : Rate