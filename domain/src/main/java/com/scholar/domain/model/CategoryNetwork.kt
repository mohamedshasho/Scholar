package com.scholar.domain.model

import com.google.gson.annotations.SerializedName

class CategoryNetwork(
    @SerializedName("id")
    override val id:Int,
    @SerializedName("category")
    override val name:String,
    @SerializedName("image")
    override val image:String?,
) : Category




interface Category{
    val id:Int
    val name:String
    val image:String?
}