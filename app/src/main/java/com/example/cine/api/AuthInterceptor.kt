package com.example.cine.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwOTM4YjAyNWJmMmE1NDRkZDE4ZDc2MWRlZjljMDZhZSIsIm5iZiI6MTczMzMzNTkxNi42ODIsInN1YiI6IjY3NTA5YjZjYTkzYmRlYThjMjUwYWY0OSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.nx-f1aiwAzNSJ4-DjJTxITQm6Qn_ugzWrIyTjLr8X5A")
            .build()
        return chain.proceed(request)
    }
}