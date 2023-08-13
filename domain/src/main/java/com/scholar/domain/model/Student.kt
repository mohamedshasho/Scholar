package com.scholar.domain.model

import com.google.gson.annotations.SerializedName

interface Student {
    val id: Int
    val fullName: String
    val email: String?
    val image: String?
    val wallet: String?
}

data class StudentNetwork(
    override val id: Int,
    @SerializedName("full_name")
    override val fullName: String,
    override val email: String?,
    override val image: String?,
    override val wallet: String?,
) : Student