package com.example.yatodo.di

import android.content.Context
import com.example.db.DaoProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): DaoProvider =
        DaoProvider(context)

    @Provides
    fun providesTaskDao(daoProvider: DaoProvider) = daoProvider.tasksDao
}