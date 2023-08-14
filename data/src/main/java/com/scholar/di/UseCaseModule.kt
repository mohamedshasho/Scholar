package com.scholar.di

import com.scholar.domain.model.NetworkConnectivity
import com.scholar.domain.repo.*
import com.scholar.domain.usecase.*
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
        classRoomRepository: ClassRoomRepository,
        networkConnectivity: NetworkConnectivity,
    ) = ClassMateUseCase(classRoomRepository, networkConnectivity)

    @Singleton
    @Provides
    fun provideSubjectUseCase(
        subjectRepository: SubjectRepository,
        networkConnectivity: NetworkConnectivity,
    ) = SubjectUseCase(subjectRepository, networkConnectivity)

    @Singleton
    @Provides
    fun provideMaterialUseCase(
        materialRepository: MaterialRepository,
        networkConnectivity: NetworkConnectivity,
    ) = MaterialUseCase(materialRepository, networkConnectivity)
    @Singleton
    @Provides
    fun provideLoginUseCase() = ValidateLoginUseCase()

    @Singleton
    @Provides
    fun provideRegisterUseCase() = ValidateRegisterUseCase()
}