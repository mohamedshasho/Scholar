package com.scholar.domain.repo

import androidx.paging.PagingData
import com.scholar.domain.model.Resource
import com.scholar.domain.model.Material
import com.scholar.domain.model.MaterialWithDetail
import com.scholar.domain.model.MaterialWithTeacher
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.net.URL

interface MaterialRepository {
    suspend fun getSomeMaterialsFromNetwork(): Resource<List<Material>>
    suspend fun getSomeMaterials(): Flow<List<MaterialWithTeacher>>
    suspend fun getMaterialsFromNetwork(): Resource<List<Material>>
    suspend fun observeMaterials(): Flow<List<Material>>
    suspend fun getMaterialFromLocal(id: Int): Flow<MaterialWithDetail>
    suspend fun getMaterialFromNetwork(id: Int)


    suspend fun searchForMaterialFromNetwork(key:String): Flow<PagingData<MaterialWithTeacher>>
    suspend fun filterMaterialFromNetwork(
        stageId :Int?,
        classroomId :Int?,
        subjectId :Int?,
        categoryId :Int?,
    ): Flow<PagingData<MaterialWithTeacher>>


    suspend fun loadPDf(url: URL, pdfBytes: (File?) -> Unit)
}