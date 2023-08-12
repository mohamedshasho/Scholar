package com.scholar.domain.model

import com.google.gson.annotations.SerializedName

data class TeacherNetwork(
    override val id: Int,
    @SerializedName("full_name")
    override val fullName: String,
    override val birth: String?,
    override val image: String?,
    override val email: String?,
    override val bio: String?,
    override val education: String?,
    override val phone: String?,
    override val qualification: String?,
    val subjects: List<SubjectNetwork>,
) : Teacher

interface Teacher {
    val id: Int
    val fullName: String
    val email: String?
    val birth: String?
    val phone: String?
    val bio: String?
    val image: String?
    val education: String?
    val qualification: String?
}