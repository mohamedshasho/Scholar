package com.scholar.data.repo

import com.scholar.data.dao.CategoryDao
import com.scholar.data.toLocal
import com.scholar.domain.model.Category
import com.scholar.domain.model.ErrorException
import com.scholar.domain.model.NetworkResult
import com.scholar.domain.repo.CategoryRepository
import com.scholar.domain.service.ApiService

class CategoryRepositoryImp(
    private val apiService: ApiService,
    private val categoryDao: CategoryDao,
) : CategoryRepository {

    override suspend fun getCategoriesFromNetwork(): List<Category> {
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
    }

    override suspend fun getCategoriesFromLocal(): List<Category> {
        return categoryDao.getCategories()
    }
}