package com.example.android_hilt_sample.di

import com.example.android_hilt_sample.repository.MainRepository
import com.example.android_hilt_sample.retrofit.BlogRetrofit
import com.example.android_hilt_sample.retrofit.NetworkMapper
import com.example.android_hilt_sample.room.BlogDao
import com.example.android_hilt_sample.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(blogDao: BlogDao, retrofit: BlogRetrofit, cacheMapper: CacheMapper, networkMapper: NetworkMapper): MainRepository {
        return MainRepository(blogDao, retrofit, cacheMapper, networkMapper)
    }
}