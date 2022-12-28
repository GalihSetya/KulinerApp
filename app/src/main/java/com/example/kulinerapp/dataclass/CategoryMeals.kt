package com.example.kulinerapp.dataclass

// 49a. Membuat data class MealX otomatis dari json menggunakan plugin Kotlin dataclass file from JSON
//rename MealX menjadi CategoryMeals
data class CategoryMeals(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)