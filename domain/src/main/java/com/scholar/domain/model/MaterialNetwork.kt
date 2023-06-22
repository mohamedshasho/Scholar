package com.scholar.domain.model



data class MaterialNetwork(
    override val id: Int,
    val id_class : Int,
    val id_subject : Int,
    val id_teacher : Int?,
    val id_category : Int?,
    override val title: String,
    override val description: String?,
    override val price: Int?,
    override val content: String?,
) : Material


interface Material {
    val id: Int
    val title: String
    val description: String?
    val price: Int?
    val content: String?
}