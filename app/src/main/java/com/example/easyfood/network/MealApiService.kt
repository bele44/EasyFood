package com.example.easyfood.network
import com.example.easyfood.model.CategoryList
import com.example.easyfood.model.MealsByCategoryList
import com.example.easyfood.model.MealList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("random.php")
    suspend fun getRandomMeal():Response<MealList>
    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i")id:String?):Response<MealList>
    @GET("filter.php")
    suspend fun getPopularItems(@Query("c")categoryName:String):Response<MealsByCategoryList>
    @GET("categories.php")
    suspend fun getCategories():Response<CategoryList>
    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c")categoryName:String):Response<MealsByCategoryList>
    @GET("search.php")
    suspend fun searchMeals(@Query("s")searchQuery:String):Response<MealList>
}