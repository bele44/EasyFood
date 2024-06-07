package com.example.easyfood.di
import android.content.Context

import com.example.easyfood.data.OfflineMealsRepository
import com.example.easyfood.data.OfflineRepository
import com.example.easyfood.db.MealDao
import com.example.easyfood.db.MealDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object OfflineModule {

    @Provides
    @Singleton
    fun provideMealDao(database: MealDatabase): MealDao {
        return database.mealDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MealDatabase {
        return MealDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideOfflineRepository(mealDao: MealDao): OfflineRepository {
        return OfflineMealsRepository(mealDao)
    }
}
