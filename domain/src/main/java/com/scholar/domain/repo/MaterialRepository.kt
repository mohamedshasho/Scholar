package com.scholar.domain.repo

import com.scholar.domain.model.Resource
import com.scholar.domain.model.Material
import com.scholar.domain.model.MaterialWithTeacher
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    suspend fun getSomeMaterialsFromNetwork(): Resource<List<Material>>
    suspend fun getSomeMaterials(): Resource<List<MaterialWithTeacher>>
    suspend fun getMaterialsFromNetwork(): Resource<List<Material>>
    suspend fun observeMaterials(): Flow<List<Material>>
    suspend fun getMaterialFromLocal(id: Int): Material
    suspend fun getMaterialFromNetwork(id: Int): Resource<Material>
}