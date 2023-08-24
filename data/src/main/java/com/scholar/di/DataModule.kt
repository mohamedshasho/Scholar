package com.scholar.di

import android.content.Context
import androidx.room.Room
import com.scholar.data.repo.*
import com.scholar.data.service.ApiService
import com.scholar.data.source.local.ScholarDb
import com.scholar.data.source.local.ScholarDbCallBack
import com.scholar.data.source.local.dao.*
import com.scholar.data.source.network.*
import com.scholar.domain.repo.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideScholarDb(
        @ApplicationContext appContext: Context,
        provider: Provider<ScholarDb>,
    ): ScholarDb {
        return Room.databaseBuilder(
            appContext,
            ScholarDb::class.java,
            "ScholarDB"
        )
            .addCallback(ScholarDbCallBack(provider))
            .build()
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
    fun provideClassMateDao(scholarDb: ScholarDb) = scholarDb.classRoomDao()


    @Singleton
    @Provides
    fun provideStudentDao(scholarDb: ScholarDb) = scholarDb.studentDao()

    @Singleton
    @Provides
    fun provideSubjectDao(scholarDb: ScholarDb) = scholarDb.subjectDao()

    @Singleton
    @Provides
    fun provideRateDao(scholarDb: ScholarDb) = scholarDb.rateDao()

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
    ): TeacherRepository =
        TeacherRepositoryImp(teacherNetworkDataSource, scholarDb)

    @Singleton
    @Provides
    fun provideStoryRepository(
        storyNetworkDataSource: StoryNetworkDataSource, scholarDb: ScholarDb,
    ): StoryRepository =
        StoryRepositoryImp(storyNetworkDataSource, scholarDb)

    @Singleton
    @Provides
    fun provideMaterialRepository(
        materialNetworkDataSource: MaterialNetworkDataSource,
        materialDao: MaterialDao,
        rateDao: RateDao, teacherDao: TeacherDao,
        @DefaultDispatcher dispatcher: CoroutineDispatcher,
    ): MaterialRepository =
        MaterialRepositoryImp(
            materialNetworkDataSource,
            materialDao,
            rateDao,
            teacherDao,
            dispatcher
        )

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
        classRoomDao: ClassRoomDao,
        @DefaultDispatcher dispatcher: CoroutineDispatcher,
    ): ClassRoomRepository =
        ClassRoomRepositoryImp(networkDataSource, classRoomDao, dispatcher)


    @Singleton
    @Provides
    fun provideSubjectRepository(
        subjectNetworkDataSource: SubjectNetworkDataSource,
        subjectDao: SubjectDao,
        @DefaultDispatcher dispatcher: CoroutineDispatcher,
    ): SubjectRepository =
        SubjectRepositoryImp(subjectNetworkDataSource, subjectDao, dispatcher)

    @Singleton
    @Provides
    fun provideAuthRepository(
        dataStorePreference: DataStorePreference,
        apiService: ApiService,
        studentDao: StudentDao,
    ): AuthRepository {
        return AuthRepositoryImp(dataStorePreference,apiService, studentDao)
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStorePreference {
        return DataStorePreferenceImp(context)
    }

    @Singleton
    @Provides
    fun provideStudentRepository(
        dataStorePreference: DataStorePreference,
        studentDao: StudentDao,
        studentNetworkDataSource: StudentNetworkDataSource,
    ): StudentRepository {
        return StudentRepositoryImp(dataStorePreference, studentNetworkDataSource, studentDao)
    }
}