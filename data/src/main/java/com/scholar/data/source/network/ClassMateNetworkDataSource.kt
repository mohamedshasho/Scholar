package com.scholar.data.source.network

import com.scholar.data.service.ApiService
import com.scholar.domain.model.ClassRoomNetwork
import com.scholar.domain.model.NetworkResult
import javax.inject.Inject

class ClassMateNetworkDataSource @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun getClassesMate() : List<ClassRoomNetwork>{
        return  when(val response = apiService.getClassRooms()){
            is NetworkResult.Success->{
                response.data
            }
            else ->{
                emptyList()
            }
        }

    }
}