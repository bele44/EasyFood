package com.example.easyfood.di

import com.example.easyfood.network.MealApiService
import com.example.easyfood.data.MealRepository
import com.example.easyfood.data.NetworkMealRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideMealApiService(retrofit: Retrofit): MealApiService {
        return retrofit.create(MealApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMealRepository(mealApiService: MealApiService): MealRepository {
        return NetworkMealRepository(mealApiService)
    }
}
