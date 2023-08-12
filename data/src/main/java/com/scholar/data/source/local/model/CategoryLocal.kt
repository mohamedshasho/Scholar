package com.scholar.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scholar.domain.model.Category
import com.scholar.domain.model.CategoryNetwork

@Entity(tableName = "categories")
data class CategoryLocal(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    override val name: String,
    override val image: Int,
) : Category


fun CategoryNetwork.toLocal() = CategoryLocal(id, name, image)

fun List<CategoryNetwork>.toLocal() = map(CategoryNetwork::toLocal)