package com.example.easyfood.ui.food.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.data.MealRepository
import com.example.easyfood.model.Category
import com.example.easyfood.model.Meal
import com.example.easyfood.model.MealsByCategory
import com.example.easyfood.model.MealList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val mealRepository: MealRepository
) : ViewModel() {

    private val _meals = MutableStateFlow<MealList?>(null)
    val meals: StateFlow<MealList?> = _meals

    private val _popularItems = MutableStateFlow<List<MealsByCategory>?>(null)
    val popularItems: StateFlow<List<MealsByCategory>?> = _popularItems

    private val _categories = MutableStateFlow<List<Category>?>(null)
    val categories: StateFlow<List<Category>?> = _categories

    private val _categoryItems = MutableStateFlow<List<MealsByCategory>>(emptyList())
    val categoryItems: StateFlow<List<MealsByCategory>> = _categoryItems

    private val _mealDetails = MutableStateFlow<Meal?>(null)
    val mealDetails: StateFlow<Meal?> = _mealDetails
     private val _searchedMeals = MutableStateFlow<List<Meal>>(emptyList())
      val searchedMeals:StateFlow<List<Meal>> = _searchedMeals
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchRandomMeal()
        fetchPopularItems("Seafood")
        fetchCategories()

    }

    fun fetchRandomMeal() {
        viewModelScope.launch {
            try {
                val response = mealRepository.getRandomMeal()
                if (response.isSuccessful) {
                    _meals.value = response.body()
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            } catch (e: IOException) {
                _error.value = "Network error: ${e.message}"
            } catch (e: HttpException) {
                _error.value = "HTTP error: ${e.code()}"
            }
        }
    }

    private fun fetchPopularItems(categoryName: String) {
        viewModelScope.launch {
            try {
                val response = mealRepository.getPopularItems(categoryName)
                if (response.isSuccessful) {
                    _popularItems.value = response.body()?.meals
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            } catch (e: IOException) {
                _error.value = "Network error: ${e.message}"
            } catch (e: HttpException) {
                _error.value = "HTTP error: ${e.code()}"
            }
        }
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = mealRepository.getCategories()
                if (response.isSuccessful) {
                    _categories.value = response.body()?.categories
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            } catch (e: IOException) {
                _error.value = "Network error: ${e.message}"
            } catch (e: HttpException) {
                _error.value = "HTTP error: ${e.code()}"
            }
        }
    }

    fun fetchCategoryItems(categoryName: String) {
        viewModelScope.launch {
            try {
                val response = mealRepository.getMealsByCategory(categoryName)
                if (response.isSuccessful) {
                    _categoryItems.value = response.body()?.meals ?: emptyList()
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            } catch (e: IOException) {
                _error.value = "Network error: ${e.message}"
            } catch (e: HttpException) {
                _error.value = "HTTP error: ${e.code()}"
            }
        }
    }

    fun fetchMealDetails(idMeal: String?) {
        viewModelScope.launch {
            try {
                val response = mealRepository.getMealDetails(idMeal)
                if (response.isSuccessful) {
                    _mealDetails.value = response.body()?.meals?.firstOrNull()
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            } catch (e: IOException) {
                _error.value = "Network error: ${e.message}"
            } catch (e: HttpException) {
                _error.value = "HTTP error: ${e.code()}"
            }
        }
    }

    fun searchMeal(searchQuery:String){
        viewModelScope.launch {
            try {
                val response = mealRepository.searchMeal(searchQuery)
                if (response.isSuccessful){
                    _searchedMeals.value = response.body()?.meals?:emptyList()
            }  else{
                    _error.value = "Error: ${response.code()}"
                }
            }
                catch (e:IOException){
                _error.value ="Network error :${e.message}"
            }catch (e:HttpException){
                _error.value = "HTTP error: ${e.code()}"
            }
        }
    }
}
