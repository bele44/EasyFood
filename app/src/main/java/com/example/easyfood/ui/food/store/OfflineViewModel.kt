package com.example.easyfood.ui.food.store

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.data.OfflineRepository
import com.example.easyfood.model.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class OfflineViewModel @Inject constructor(private val offlineRepository: OfflineRepository) : ViewModel() {

    private val _offlineMeals = MutableStateFlow<List<Meal>>(emptyList())
    val offlineMeals: StateFlow<List<Meal>> get() = _offlineMeals

    private val _upsertedMeal = MutableStateFlow<Meal?>(null)
    val upsertedMeal: StateFlow<Meal?> get() = _upsertedMeal

    private val _deletedMeal = MutableStateFlow<Meal?>(null)
    val deletedMeal: StateFlow<Meal?> get() = _deletedMeal

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite: StateFlow<Boolean> get() = _isFavorite

    private var lastDeletedMeal: Meal? = null

    //
    private val _offlineMealById = MutableStateFlow<Meal?>(null)
    val offlineMealById: StateFlow<Meal?> get() = _offlineMealById

    init {
        Log.d("OfflineViewModel", "ViewModel initialized, calling getAllMeals")
        getAllMeals()
    }

    fun fetchMealDetails(idMeal: String?) {
        idMeal?.let { id ->
            viewModelScope.launch(Dispatchers.IO) {
                offlineRepository.getMealStream(id)
                    .catch { e ->
                        Log.e("OfflineViewModel", "Error fetching meal details: ${e.message}")
                        reportError("Error fetching meal details: ${e.message}")
                    }
                    .collect { meal ->
                        Log.d("OfflineViewModel", "Meal details fetched: $meal")
                        _offlineMealById.value = meal
                        checkIfFavorite(id)
                    }
            }
        } ?: run {
            Log.e("OfflineViewModel", "Error: idMeal is null")
            _error.value = "Error: idMeal is null"
        }
    }

    private fun getAllMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            offlineRepository.getAllMeals()
                .catch { e ->
                    Log.e("OfflineViewModel", "Error fetching meals: ${e.message}")
                    reportError("Error fetching meals: ${e.message}")
                }
                .collect { meals ->
                    Log.d("OfflineViewModel", "Meals fetched: $meals")
                    _offlineMeals.value = meals
                }
        }
    }

    fun upsertMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                offlineRepository.upsertMeal(meal)
                _upsertedMeal.value = meal
                checkIfFavorite(meal.idMeal)
                getAllMeals()
            } catch (e: Exception) {
                reportError("Error upserting meal: ${e.message}")
            }
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                offlineRepository.deleteMeal(meal)
                lastDeletedMeal = meal
                _deletedMeal.value = meal
                checkIfFavorite(meal.idMeal)
                getAllMeals()
            } catch (e: Exception) {
                reportError("Error deleting meal: ${e.message}")
            }
        }
    }

    private fun checkIfFavorite(idMeal: String) {
        viewModelScope.launch {
            offlineRepository.getMealStream(idMeal)
                .collect { meal ->
                    _isFavorite.value = meal != null
                }
        }
    }

    fun undoDeleteMeal() {
        lastDeletedMeal?.let { meal ->
            upsertMeal(meal)
            lastDeletedMeal = null
        }
    }

    private fun clearError() {
        _error.value = null
    }

    private suspend fun reportError(message: String) {
        _error.value = message
        delay(3000) // example delay
        clearError()
    }

    fun clearDeletedMeal() {
        _deletedMeal.value = null
    }
}
