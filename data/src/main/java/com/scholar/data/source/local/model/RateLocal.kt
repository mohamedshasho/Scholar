package com.scholar.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.scholar.domain.model.Rate


@Entity(
    tableName = "rates",
    foreignKeys = [ForeignKey(
        entity = MaterialLocal::class,
        parentColumns = ["id"], childColumns = ["material_id"]
    )]
)
data class RateLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "material_id")
    val materialId: Int,
    override val rate: Int,
    override val comment: String?,
) : Rate
