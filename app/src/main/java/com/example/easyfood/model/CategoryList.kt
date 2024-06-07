package com.example.easyfood.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryList(
    val categories: List<Category>
)