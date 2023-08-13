package com.scholar.data.source.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.scholar.domain.model.Material
import com.scholar.domain.model.MaterialNetwork

@Entity(tableName = "materials")
data class MaterialLocal(
    @PrimaryKey
    override val id: Int,
    val classId: Int?,
    val subjectId: Int?,
    val teacherId: Int?,
    override val categoryId: Int?,
    override val title: String,
    override val description: String?,
    override val price: Int?,
    override val discount: Int?,
    override val hoursNumberOfWeek: Int?,
) : Material


fun MaterialNetwork.toLocal() = MaterialLocal(
    id,
    classId = null,
    subjectId = null,
    teacherId = teacher?.id,
    categoryId = categoryId,
    title = title,
    description = description,
    price = price,
    discount=discount,
    hoursNumberOfWeek=hoursNumberOfWeek,
)

fun List<MaterialNetwork>.toLocal() = map(MaterialNetwork::toLocal)
