package com.scholar.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.scholar.domain.model.Rate
import com.scholar.domain.model.RateNetwork


@Entity(
    tableName = "rates",
    primaryKeys = ["student_id", "material_id"],
)
data class RateLocal(
    @ColumnInfo(name = "student_id")
    val studentId: Int,
    @ColumnInfo(name = "material_id")
    val materialId: Int,
    override val rate: Int,
    override val comment: String?,
) : Rate


fun RateNetwork.toLocal() =
    RateLocal(materialId = materialId, studentId = studentId, rate = rate, comment = comment)

fun List<RateNetwork>.toLocal() = map(RateNetwork::toLocal)
