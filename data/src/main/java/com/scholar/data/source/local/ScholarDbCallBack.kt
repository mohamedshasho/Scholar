package com.scholar.data.source.local

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.scholar.data.source.local.model.CategoryLocal
import com.scholar.data.source.local.model.ClassRoomLocal
import com.scholar.data.source.local.model.StageLocal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider

class ScholarDbCallBack(
    private val provider: Provider<ScholarDb>,
) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            provider.get().categoryDao().upsertAll(categories())
            provider.get().stageDao().upsertAll(stages())
            provider.get().classRoomDao().upsertAll(classrooms())
        }
    }
}


private fun categories(): List<CategoryLocal> {
    return listOf(
        CategoryLocal(id = 1, name = "نوط", image = 1),
        CategoryLocal(id = 2, name = "ملخصات", image = 2),
        CategoryLocal(id = 3, name = "فيدوهات", image = 3),
        CategoryLocal(id = 4, name = "كتب", image = 4),
        CategoryLocal(id = 5, name = "دورات", image = 5),
    )
}

private fun stages(): List<StageLocal> {
    return listOf(
        StageLocal(id = 1, name = "المرحلة الإبتدائية"),
        StageLocal(id = 2, name = "المرحلة الإعدادية"),
        StageLocal(id = 3, name = "المرحلة الثانوية (علمي)"),
        StageLocal(id = 4, name = "المرحلة الثانوية (ادبي)"),
    )
}

private fun classrooms(): List<ClassRoomLocal> {
    return listOf(
        ClassRoomLocal(id = 1, name = "الأول"),
        ClassRoomLocal(id = 2, name = "الثاني"),
        ClassRoomLocal(id = 3, name = "الثالث"),
        ClassRoomLocal(id = 4, name = "الرابع"),
        ClassRoomLocal(id = 5, name = "الخامس"),
        ClassRoomLocal(id = 6, name = "السادس"),
    )
}