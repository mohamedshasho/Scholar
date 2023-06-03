package com.scholar.data.repo

import com.scholar.data.dao.CategoryDao
import com.scholar.data.toLocal
import com.scholar.domain.model.*
import com.scholar.domain.repo.CategoryRepository
import com.scholar.domain.service.ApiService
import kotlinx.coroutines.delay

class CategoryRepositoryImp(
    private val apiService: ApiService,
    private val categoryDao: CategoryDao,
) : CategoryRepository {

    override suspend fun getCategoriesFromNetwork(): Resource<List<Category>> {

        delay(2000)
        val data = dummyData().map { categoryNetwork ->
            categoryDao.insert(categoryNetwork.toLocal())
            categoryNetwork
        }
        return Resource.Success(data)

        /*
        *
        return when (val response = apiService.getCategories()) {
            is NetworkResult.Success -> {
                response.data.map { categoryNetwork ->
                    categoryDao.insert(categoryNetwork.toLocal())
                    categoryNetwork
                }
            }
            is NetworkResult.Error -> {
                if (response.error != null)
                    throw ErrorException.DynamicErrorException(response.error!!)
                else throw ErrorException.UnknownErrorException
            }
            is NetworkResult.Exception -> {
                throw ErrorException.NetworkErrorException
            }
        }

         */
    }

    private fun dummyData() =
        listOf(
            CategoryNetwork(id = 1, name = "Category 1", null),
            CategoryNetwork(id = 2, name = "Category 2", null),
            CategoryNetwork(id = 3, name = "Category 3", null),
            CategoryNetwork(id = 4, name = "Category 4", null),
        )


    override suspend fun getCategoriesFromLocal(): List<Category> {
        return categoryDao.getCategories()
    }
}