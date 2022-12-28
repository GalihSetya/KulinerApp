package com.example.kulinerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.kulinerapp.database.MealDatabase
import com.example.kulinerapp.viewModel.HomeViewModelFactory
import com.example.kulinerapp.viewModel.HomeviewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    //68a. Mengganti kode 67a menjadi seperti berikut
    val viewModel: HomeviewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelProviderFactory)[HomeviewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Menginflate Bottom navigation dan setting dengan navigation controller
        // 6. Inflate Bottom Navigation
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btm_nav)
        // 7. Inflate Navigation Controller
        val navController = Navigation.findNavController(this,R.id.home_fragment)
        // 8. Set Up Navigation Controller dengan Bottom Navigation kemudian cek aplikasi
        NavigationUI.setupWithNavController(bottomNavigation,navController)
    }
}