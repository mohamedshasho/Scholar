package com.scholar.domain.model

import com.google.gson.annotations.SerializedName


data class StoryNetwork(
    override val id: Int,
    override val title: String,
    override val description: String?,
    override val image: String?,
    @SerializedName("date_publication")
    override val datePublication: String?,
    @SerializedName("name")
    override val studentName: String?,
    @SerializedName("profile")
    override val studentProfile: String?,
) : Story


interface Story {
    val id: Int
    val title: String
    val description: String?
    val image: String?
    val datePublication: String?
    val studentName : String?
    val studentProfile : String?
}