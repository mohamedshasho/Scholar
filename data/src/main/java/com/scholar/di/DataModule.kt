package com.scholar.di

import android.content.Context
import androidx.room.Room
import com.scholar.data.ScholarDb
import com.scholar.data.dao.CategoryDao
import com.scholar.data.repo.CategoryRepositoryImp
import com.scholar.domain.repo.CategoryRepository
import com.scholar.domain.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideCategoryRepository(apiService: ApiService,categoryDao: CategoryDao): CategoryRepository =
        CategoryRepositoryImp(apiService,categoryDao)


}