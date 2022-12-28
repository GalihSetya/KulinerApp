package com.example.kulinerapp.dataclass

// 49b. Membuat data class MealX otomatis dari json menggunakan plugin Kotlin dataclass file from JSON
data class CategoryList(
    val meals: List<CategoryMeals>
)