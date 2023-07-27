package com.scholar.domain.model

import com.google.gson.annotations.SerializedName

data class TeacherNetwork(
    override val id: Int,
    @SerializedName("full_name")
    override val fullName: String,
    override val age: Int?,
    override val profile: String?,
    override val email: String?,
    override val bio: String?,
    override val education: String?,
    override val gender: Int,
    override val phone: String?,
    override val skills: String?,
    override val qualification: String?,
    override val views: Int,
    val subjects: List<SubjectNetwork>,
) : Teacher

interface Teacher {
    val id: Int
    val fullName: String
    val email: String?
    val age: Int?
    val gender: Int
    val phone: String?
    val bio: String?
    val profile: String?
    val education: String?
    val skills: String?
    val qualification: String?
    val views: Int
}