package com.scholar.di

import okhttp3.Interceptor
import okhttp3.Response


object HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}