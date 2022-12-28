package com.example.kulinerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kulinerapp.database.MealDatabase

//66a. Membuat kotlin class/file HomeViewModelFactory
class HomeViewModelFactory(
    private val mealDatabase: MealDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeviewModel(mealDatabase) as T
    }
}