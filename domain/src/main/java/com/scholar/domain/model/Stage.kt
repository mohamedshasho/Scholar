package com.scholar.domain.model

interface Stage {
    val id: Int
    val name: String
}


data class StageNetwork(
    override val id: Int,
    override val name: String,
) : Stage