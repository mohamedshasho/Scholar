package com.scholar.di

import android.content.Context
import androidx.room.Room
import com.scholar.data.source.local.ScholarDb
import com.scholar.data.source.local.dao.CategoryDao
import com.scholar.data.source.local.dao.MaterialDao
import com.scholar.data.source.local.dao.StoryDao
import com.scholar.data.source.local.dao.TeacherDao
import com.scholar.data.repo.CategoryRepositoryImp
import com.scholar.data.repo.MaterialRepositoryImp
import com.scholar.data.repo.StoryRepositoryImp
import com.scholar.data.repo.TeacherRepositoryImp
import com.scholar.data.source.network.CategoryNetworkDataSource
import com.scholar.data.source.network.MaterialNetworkDataSource
import com.scholar.data.source.network.StoryNetworkDataSource
import com.scholar.data.source.network.TeacherNetworkDataSource
import com.scholar.domain.repo.CategoryRepository
import com.scholar.domain.repo.MaterialRepository
import com.scholar.domain.repo.StoryRepository
import com.scholar.domain.repo.TeacherRepository
import com.scholar.data.source.network.service.ApiService
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
        teacherDao: TeacherDao,
        @DefaultDispatcher dispatcher: CoroutineDispatcher,
    ): TeacherRepository =
        TeacherRepositoryImp(teacherNetworkDataSource, teacherDao, dispatcher)

    @Singleton
    @Provides
    fun provideStoryRepository(
        storyNetworkDataSource: StoryNetworkDataSource, storyDao: StoryDao,
        @DefaultDispatcher dispatcher: CoroutineDispatcher,
    ): StoryRepository =
        StoryRepositoryImp(storyNetworkDataSource, storyDao, dispatcher)

    @Singleton
    @Provides
    fun provideMaterialRepository(
        materialNetworkDataSource: MaterialNetworkDataSource,
        materialDao: MaterialDao,
        @DefaultDispatcher  dispatcher: CoroutineDispatcher,
    ): MaterialRepository =
        MaterialRepositoryImp(materialNetworkDataSource, materialDao,dispatcher)

}