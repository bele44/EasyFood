package com.example.easyfood.model

import kotlinx.serialization.Serializable


@Serializable
data class MealList(
    val meals: List<Meal>?
)