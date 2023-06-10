package com.scholar.domain.model



data class MaterialNetwork(
    override val id: Int,
    override val title: String,
    override val description: String?,
    override val price: Double?,
    override val content: String?,
) : Material


interface Material {
    val id: Int
    val title: String
    val description: String?
    val price: Double?
    val content: String?
}