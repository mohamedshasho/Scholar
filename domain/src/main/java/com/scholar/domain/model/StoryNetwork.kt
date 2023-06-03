package com.scholar.domain.model



data class StoryNetwork(
    override val id: Int,
    override val title: String,
    override val description: String?,
    override val image: String?,
) : Story


interface Story {
    val id: Int
    val title: String
    val description: String?
    val image: String?
}