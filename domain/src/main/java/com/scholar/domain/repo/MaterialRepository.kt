package com.scholar.domain.repo

import com.scholar.domain.model.Resource
import com.scholar.domain.model.Material
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    suspend fun getSomeMaterialsFromNetwork(): Resource<List<Material>>
    suspend fun getSomeMaterials(): Resource<List<Material>>
    suspend fun getMaterialsFromNetwork(): Resource<List<Material>>
    suspend fun observeMaterials(): Flow<List<Material>>
}