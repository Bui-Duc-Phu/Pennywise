package com.example.pennywise.di

import com.example.pennywise.chatNetwork.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Tăng thời gian kết nối
            .readTimeout(30, TimeUnit.SECONDS)   // Tăng thời gian đọc dữ liệu
            .writeTimeout(30, TimeUnit.SECONDS)  // Tăng thời gian ghi dữ liệu
            .retryOnConnectionFailure(true)      // Tự động thử lại khi lỗi kết nối
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.deepseek.com/")
            .client(okHttpClient) // Sử dụng OkHttpClient với timeout
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
