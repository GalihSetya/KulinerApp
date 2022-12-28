package com.example.kulinerapp.network

import com.example.kulinerapp.dataclass.CategoryList
import com.example.kulinerapp.dataclass.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//13. Membuat Interface kontlin class/file
interface MealApi {
    //14. Membuat fungsi untuk mendapatkan data RandomMeal
    // Mengambil data class MealList
    // Menggunakan GET untuk memberitau retrofit jika ingin mengambil data dari Api
    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    //42. Menambahkan GET untuk mendapatkan informasi meal dari https://www.themealdb.com/api/json/v1/1/  lookup.php?  i=52772
    @GET("lookup.php?")
    fun getMealDetails(@Query("i")id:String) : Call<MealList>

    //51. Menambahkan GET untuk mendapatkan informasi meal dari https://www.themealdb.com/api/json/v1/1/  filter.php?  c=Seafood
    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName:String) : Call<CategoryList>

}