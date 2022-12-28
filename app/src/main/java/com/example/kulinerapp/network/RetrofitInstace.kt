package com.example.kulinerapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//15. Membuat Instace, buat object kotlin class/file untuk Membangun/Build Api
object RetrofitInstace {
    //16. Inisialisasi instance dari Api
    // by lazy berarti ini akan menginitialize value ini ketika membuat sebuah instance dari ini
    val api:MealApi by lazy {
        Retrofit.Builder()
             //Membuat Base URL
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            //Menambahkan conventor yang menagabil json file dan convert ke kotlin object
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            //Interface as Parameter
            .create(MealApi::class.java)
    }


}