package com.scholar.domain.repo

import androidx.paging.PagingData
import com.scholar.domain.model.Resource
import com.scholar.domain.model.Material
import com.scholar.domain.model.MaterialWithDetail
import com.scholar.domain.model.MaterialWithTeacher
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    suspend fun getSomeMaterialsFromNetwork(): Resource<List<Material>>
    suspend fun getSomeMaterials(): Flow<List<MaterialWithTeacher>>
    suspend fun getMaterialsFromNetwork(): Resource<List<Material>>
    suspend fun observeMaterials(): Flow<List<Material>>
    suspend fun getMaterialFromLocal(id: Int): Flow<MaterialWithDetail>
    suspend fun getMaterialFromNetwork(id: Int)


    suspend fun searchForMaterialFromNetwork(key:String): Flow<PagingData<MaterialWithTeacher>>

}