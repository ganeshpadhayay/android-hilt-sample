package com.example.android_hilt_sample.repository

import com.example.android_hilt_sample.model.Blog
import com.example.android_hilt_sample.retrofit.BlogRetrofit
import com.example.android_hilt_sample.retrofit.NetworkMapper
import com.example.android_hilt_sample.room.BlogDao
import com.example.android_hilt_sample.room.CacheMapper
import com.example.android_hilt_sample.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository(private val blogDao: BlogDao,
                     private val blogRetrofit: BlogRetrofit,
                     private val cacheMapper: CacheMapper,
                     private val networkMapper: NetworkMapper) {

    suspend fun getBlogs(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkBlogs = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for (blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}