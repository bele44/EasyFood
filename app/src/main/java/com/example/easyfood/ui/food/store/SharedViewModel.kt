package com.example.easyfood.ui.food.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.model.MealsByCategory
import com.example.easyfood.model.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    private var _selectedMeal = MutableStateFlow<Meal?>(null)
    val selectedMeal: StateFlow<Meal?>get() = _selectedMeal

    private var _selectedPopularMeal = MutableStateFlow<MealsByCategory?>(null)
    val selectedPopularMeal: StateFlow<MealsByCategory?>get() = _selectedPopularMeal

    fun selectMeal(meal: Meal) {
        viewModelScope.launch {
            _selectedMeal.value = meal
        }
    }
    fun selectPopularMeal(meal: MealsByCategory) {
        viewModelScope.launch {
            _selectedPopularMeal.value = meal
        }
    }



        private val _bottomSheetState = MutableStateFlow(false)
        val bottomSheetState: StateFlow<Boolean> = _bottomSheetState

        fun toggleBottomSheet(show: Boolean) {
            _bottomSheetState.value = show
        }

}

