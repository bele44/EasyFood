package com.example.easyfood.data

import com.example.easyfood.model.CategoryList
import com.example.easyfood.model.MealsByCategoryList
import com.example.easyfood.model.MealList
import com.example.easyfood.network.MealApiService
import retrofit2.Response
import javax.inject.Inject

interface MealRepository {
    suspend fun getRandomMeal(): Response<MealList>
    suspend fun getMealDetails(idMeal:String?):Response<MealList>
    suspend fun getPopularItems(categoryName: String): Response<MealsByCategoryList>
    suspend fun getCategories():Response<CategoryList>
    suspend fun getMealsByCategory(categoryName: String): Response<MealsByCategoryList>
    suspend fun searchMeal(searchQuery:String):Response<MealList>
}

class NetworkMealRepository @Inject constructor(private val mealApiService: MealApiService) : MealRepository {
    override suspend fun getRandomMeal(): Response<MealList> {
        return mealApiService.getRandomMeal()
    }

    override suspend fun getMealDetails(idMeal: String?): Response<MealList> {
        return mealApiService.getMealDetails(idMeal)
    }

    override suspend fun getPopularItems(categoryName: String): Response<MealsByCategoryList> {
        return mealApiService.getPopularItems(categoryName)
    }

    override suspend fun getCategories(): Response<CategoryList> {
        return mealApiService.getCategories()
    }

    override suspend fun getMealsByCategory(categoryName: String): Response<MealsByCategoryList> {
        return mealApiService.getMealsByCategory(categoryName)
    }

    override suspend fun searchMeal(searchQuery: String): Response<MealList> {
        return mealApiService.searchMeals(searchQuery)
    }
}
