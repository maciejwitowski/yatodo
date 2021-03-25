package com.example.yatodo.di

import com.example.yatodo.api.YatodoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun providesYatodoService(): YatodoService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://google.com") // TODO Just for testing the request/response flow works
            .build()

        return retrofit.create(YatodoService::class.java)
    }
}