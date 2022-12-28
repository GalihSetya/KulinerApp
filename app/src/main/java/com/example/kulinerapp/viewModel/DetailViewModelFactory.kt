package com.example.kulinerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kulinerapp.database.MealDatabase

//63. Membuat kotlin class/file MealViewModelFactory
class DetailViewModelFactory(
    private val mealDatabase: MealDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(mealDatabase) as T
    }
}