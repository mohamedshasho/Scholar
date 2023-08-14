package com.scholar.domain.model


data class MaterialWithTeacherNetwork(
    override val material: Material,
    override val teacher: TeacherSmall,
    override val totalRate: Double
)
    :MaterialWithTeacher


fun TeacherNetwork.toSmall() = TeacherSmallNetwork(
    teacherId = id,
    name = fullName,
    image = image,
)

data class TeacherSmallNetwork(
    override val teacherId: Int?,
    override val name: String?,
    override  val image: String?
) :TeacherSmall


interface TeacherSmall {
    val teacherId: Int?
    val name: String?
    val image: String?
}

interface MaterialWithTeacher {
    val material: Material
    val totalRate: Double
    val teacher: TeacherSmall
}

interface MaterialWithDetail {
    val material: Material
    val teacher: TeacherSmall
    val rates :List<Rate>
    val classroom :String?
    val stage :String?
    val subject :String?
    val category :String?
}





