package com.scholar.di

import android.content.Context
import com.scholar.domain.model.NetworkConnectivity
import com.scholar.domain.model.NetworkConnectivityChecker
import com.scholar.domain.model.NetworkResultCallAdapterFactory
import com.scholar.domain.repo.CategoryRepository
import com.scholar.domain.usecase.CategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): com.scholar.data.source.network.service.ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://127.0.0.1:8000")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .build()

        return retrofit.create(com.scholar.data.source.network.service.ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkConnection(@ApplicationContext context: Context): NetworkConnectivity =
        NetworkConnectivityChecker(context)

}