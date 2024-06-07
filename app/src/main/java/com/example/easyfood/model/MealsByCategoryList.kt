package com.example.easyfood.model
import kotlinx.serialization.Serializable


@Serializable
data class MealsByCategoryList(
    val meals: List<MealsByCategory>
)