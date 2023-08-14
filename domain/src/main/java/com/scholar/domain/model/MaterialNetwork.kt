package com.scholar.domain.model

import com.google.gson.annotations.SerializedName


data class MaterialNetwork(
    override val id: Int,
    override val title: String,
    override val description: String?,
    override val price: Int?,
    override val discount: Int?,
    @SerializedName("hours_number_of_week")
    override val hoursNumberOfWeek: Int?,
    @SerializedName("file_type")
    override val categoryId: Int?,
    val teacher: TeacherNetwork,
    val rates : List<RateNetwork>,
    val classroom: ClassRoomCrossRefNetwork?,
    val subject: SubjectNetwork?,
    val pdf : String?,
    val videos : String?,
    val summaries : String?,
    val book : String?,
    val exam : String?,
    override val content: String?,
) : Material


interface Material {
    val id: Int
    val title: String
    val description: String?
    val price: Int?
    val discount: Int?
    val hoursNumberOfWeek: Int?
    val categoryId: Int?
    val content: String?
}