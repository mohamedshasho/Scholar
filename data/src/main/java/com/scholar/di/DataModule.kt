package com.scholar.di

import android.content.Context
import androidx.room.Room
import com.scholar.data.repo.*
import com.scholar.data.source.local.ScholarDb
import com.scholar.data.source.local.dao.*
import com.scholar.data.source.network.*
import com.scholar.domain.repo.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideScholarDb(
        @ApplicationContext appContext: Context,
    ): ScholarDb {
        return Room.databaseBuilder(
            appContext,
            ScholarDb::class.java,
            "ScholarDB"
        ).build()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(scholarDb: ScholarDb) = scholarDb.categoryDao()

    @Singleton
    @Provides
    fun provideStoryDao(scholarDb: ScholarDb) = scholarDb.storyDao()

    @Singleton
    @Provides
    fun provideMaterialDao(scholarDb: ScholarDb) = scholarDb.materialDao()

    @Singleton
    @Provides
    fun provideTeacherDao(scholarDb: ScholarDb) = scholarDb.teacherDao()

    @Singleton
    @Provides
    fun provideStageDao(scholarDb: ScholarDb) = scholarDb.stageDao()

    @Singleton
    @Provides
    fun provideClassMateDao(scholarDb: ScholarDb) = scholarDb.classMateDao()

    @Singleton
    @Provides
    fun provideCategoryRepository(
        categoryNetworkDataSource: CategoryNetworkDataSource,
        categoryDao: CategoryDao,
        @DefaultDispatcher dispatcher: CoroutineDispatcher,
    ): CategoryRepository =
        CategoryRepositoryImp(categoryNetworkDataSource, categoryDao, dispatcher)

    @Singleton
    @Provides
    fun provideTeacherRepository(
        teacherNetworkDataSource: TeacherNetworkDataSource,
        scholarDb: ScholarDb,
        @DefaultDispatcher dispatcher: CoroutineDispatcher,
    ): TeacherRepository =
        TeacherRepositoryImp(teacherNetworkDataSource, scholarDb, dispatcher)

    @Singleton
    @Provides
    fun provideStoryRepository(
        storyNetworkDataSource: StoryNetworkDataSource, scholarDb: ScholarDb,
        @DefaultDispatcher dispatcher: CoroutineDispatcher,
    ): StoryRepository =
        StoryRepositoryImp(storyNetworkDataSource, scholarDb, dispatcher)

    @Singleton
    @Provides
    fun provideMaterialRepository(
        materialNetworkDataSource: MaterialNetworkDataSource,
        materialDao: MaterialDao,
        @DefaultDispatcher dispatcher: CoroutineDispatcher,
    ): MaterialRepository =
        MaterialRepositoryImp(materialNetworkDataSource, materialDao, dispatcher)

    @Singleton
    @Provides
    fun provideStageRepository(
        stageNetworkDataSource: StageNetworkDataSource,
        stageDao: StageDao,
        @DefaultDispatcher dispatcher: CoroutineDispatcher,
    ): StageRepository =
        StageRepositoryImp(stageNetworkDataSource, stageDao, dispatcher)

    @Singleton
    @Provides
    fun provideClassMateRepository(
        networkDataSource: ClassMateNetworkDataSource,
        classMateDao: ClassMateDao,
        @DefaultDispatcher dispatcher: CoroutineDispatcher,
    ): ClassMateRepository =
        ClassMateRepositoryImp(networkDataSource, classMateDao, dispatcher)

}