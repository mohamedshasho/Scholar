package com.scholar.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scholar.domain.model.Category

@Entity(tableName = "categories")
data class CategoryLocal(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    override val name: String,
    override val image: String?,
) : Category