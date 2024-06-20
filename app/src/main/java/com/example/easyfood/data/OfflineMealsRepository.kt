package com.example.easyfood.data

import com.example.easyfood.db.MealDao
import com.example.easyfood.model.Meal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface OfflineRepository {
fun getAllMeals(): Flow<List<Meal>>

suspend fun upsertMeal(meal: Meal)

suspend fun deleteMeal(meal: Meal)

    fun getMealStream(id: String): Flow<Meal?>

}

class OfflineMealsRepository @Inject constructor(private val mealDao: MealDao) : OfflineRepository {
    override fun getAllMeals(): Flow<List<Meal>> = mealDao.getAllMeals()
    override suspend fun upsertMeal(meal: Meal) = mealDao.upsertMeal(meal)
    override suspend fun deleteMeal(meal: Meal) = mealDao.deleteMeal(meal)

    override fun getMealStream(id: String): Flow<Meal?> = mealDao.getMealById(id)
}