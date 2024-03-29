package com.scholar.domain.model

import com.google.gson.annotations.SerializedName

interface Student {
    val id: Int
    val fullName: String
    val email: String?
    val image: String?
    val wallet: Int?
}

data class StudentNetwork(
    override val id: Int,
    @SerializedName("full_name")
    override val fullName: String,
    override val email: String?,
    override val image: String?,
    @SerializedName("account")
    override val wallet: Int?,
    val material: List<MaterialNetwork>?
) : Student