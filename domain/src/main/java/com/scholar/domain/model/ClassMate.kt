package com.scholar.domain.model

import com.google.gson.annotations.SerializedName

interface ClassMate {
    val id: Int
    val name: String
}

data class ClassMateNetwork(
    override val id: Int,
    @SerializedName("stage_id")
    val stageId: Int,
    override val name: String,
) : ClassMate