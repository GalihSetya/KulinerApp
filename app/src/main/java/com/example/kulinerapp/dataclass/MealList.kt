package com.example.kulinerapp.dataclass

// 12 b. Membuat data class MealList otomatis dari json menggunakan plugin Kotlin dataclass file from JSON
data class MealList(
    val meals: List<Meal>
)