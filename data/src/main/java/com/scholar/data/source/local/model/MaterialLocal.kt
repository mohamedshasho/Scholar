package com.scholar.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scholar.domain.model.Material
import com.scholar.domain.model.MaterialNetwork

@Entity(tableName = "materials")
data class MaterialLocal(
    @PrimaryKey
    override val id: Int,
    override val title: String,
    override val description: String?,
    override val price: Double?,
    override val content: String?,
) : Material


fun MaterialNetwork.toLocal() = MaterialLocal(id, title, description, price, content)
fun List<MaterialNetwork>.toLocal() = map(MaterialNetwork::toLocal)
