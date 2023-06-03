package com.scholar.domain.model

import com.google.gson.annotations.SerializedName

data class TeacherNetwork(
    override val id: Int,
    override val name: String,
    override val age: Int?,
    override val profile: String?,
    override val bio: String?,
    @SerializedName("last_seen")
    override val lastSeen: String?,
    override val skills: String?,
    override val qualification: String?,
    override val views: Int,
    ) : Teacher

interface Teacher {
    val id: Int
    val name: String
    val age: Int?
    val bio: String?
    val profile: String?
    val lastSeen: String?
    val skills: String?
    val qualification: String?
    val views: Int
}