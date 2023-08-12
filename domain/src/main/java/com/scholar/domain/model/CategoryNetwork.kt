package com.scholar.domain.model


class CategoryNetwork(
    override val id:Int,
    override val name:String,
    override val image: Int,
) : Category

interface Category{
    val id:Int
    val name:String
    val image: Int
}