package com.scholar.di

import com.scholar.domain.model.NetworkConnectivity
import com.scholar.domain.repo.CategoryRepository
import com.scholar.domain.usecase.CategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Singleton
    @Provides
    fun provideCategoryUseCase(
        categoryRepository: CategoryRepository,
        networkConnectivity: NetworkConnectivity,
    ): CategoryUseCase {
        return CategoryUseCase(categoryRepository, networkConnectivity)
    }
}