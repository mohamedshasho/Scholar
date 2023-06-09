package com.scholar.data.source.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.scholar.domain.model.Material
import com.scholar.domain.model.MaterialNetwork

@Entity(
    tableName = "materials",
    foreignKeys = [
        ForeignKey(
            childColumns = ["teacherId"],
            entity = TeacherLocal::class,
            parentColumns = ["id"]
        ),
        ForeignKey(
            childColumns = ["categoryId"],
            entity = CategoryLocal::class,
            parentColumns = ["id"]
        ),
        ForeignKey(
            childColumns = ["subjectId"],
            entity = SubjectLocal::class,
            parentColumns = ["id"]
        ),
        ForeignKey(
            childColumns = ["classId"],
            entity = ClassMateLocal::class,
            parentColumns = ["id"]
        ),

    ]
)
data class MaterialLocal(
    @PrimaryKey
    override val id: Int,
    val classId: Int,
    val subjectId: Int,
    val teacherId: Int?,
    val categoryId: Int?,
    override val title: String,
    override val description: String?,
    override val price: Int?,
    override val content: String?,
) : Material


fun MaterialNetwork.toLocal() = MaterialLocal(
    id,
    classId = id_class,
    subjectId = id_subject,
    teacherId = id_teacher,
    categoryId = id_category,
    title = title,
    description = description,
    price = price,
    content = content
)

fun List<MaterialNetwork>.toLocal() = map(MaterialNetwork::toLocal)
