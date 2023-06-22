package com.scholar.di

import com.scholar.domain.model.NetworkConnectivity
import com.scholar.domain.repo.CategoryRepository
import com.scholar.domain.repo.ClassMateRepository
import com.scholar.domain.repo.StageRepository
import com.scholar.domain.usecase.CategoryUseCase
import com.scholar.domain.usecase.ClassMateUseCase
import com.scholar.domain.usecase.StageUseCase
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

    @Singleton
    @Provides
    fun provideStageUseCase(
        stageRepository: StageRepository,
        networkConnectivity: NetworkConnectivity,
    ) = StageUseCase(stageRepository, networkConnectivity)

    @Singleton
    @Provides
    fun provideClassMateUseCase(
        classMateRepository: ClassMateRepository,
        networkConnectivity: NetworkConnectivity,
    ) = ClassMateUseCase(classMateRepository, networkConnectivity)

}