package com.scholar.domain.model

import com.google.gson.annotations.SerializedName


data class StoryNetwork(
    override val id: Int,
    override val title: String,
    override val description: String?,
    override val image: String?,
    @SerializedName("createdAt")
    override val datePublication: String?,
    @SerializedName("student")
    val student : StudentNetwork
) : Story




interface Story {
    val id: Int
    val title: String
    val description: String?
    val image: String?
    val datePublication: String?
}