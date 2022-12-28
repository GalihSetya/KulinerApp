package com.example.kulinerapp.viewModel

import androidx.lifecycle.*
import com.example.kulinerapp.dataclass.CategoryList
import com.example.kulinerapp.dataclass.CategoryMeals
import com.example.kulinerapp.dataclass.Meal
import com.example.kulinerapp.dataclass.MealList
import com.example.kulinerapp.database.MealDatabase
import com.example.kulinerapp.network.RetrofitInstace
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//24. Membuat kotlin class/file untuk viewModel home screen
//25. Menginherit dari viewer model class (Menambahakan ->> ():ViewModel())
class HomeviewModel(
    //65a. Membuat Instace agar dapat menggunakan meal dell
    private val mealDatabase: MealDatabase
):ViewModel(){

    //29. Menambahkan private var randomMealLiveData = MutableLiveData<Meal>() mengambil Meal data class
    private var randomMealLiveData = MutableLiveData<Meal>()
    //52b. Menbuat Livedata untuk populatIntems
    private var popularItemsLiveData = MutableLiveData<List<CategoryMeals>>()
    //65b. Membuat LiveData untuk database
    private var favoritesMealsLiveData = mealDatabase.mealDao().getAllMeals()
    // membuat value untuk binding
    val menurandom: LiveData<Meal> = randomMealLiveData

    init {
        getRadomMeal()
    }
    //26. Membuat fungsi untuk mendapatkan data radommeal
    fun getRadomMeal(){
        //27 a. Menuju ke HomeMenuFragment kemudian cut dan paste Retrofit instance
        RetrofitInstace.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                //Jika terkoneksi
                if(response.body() !=null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    //30. Menerapkan RandomMealLiveData
                    randomMealLiveData.value = randomMeal

                    // 28. Hapus kode dibawah
                   /** Glide.with(this@HomeMenuFragment)
                        .load(randomMeal.strMealThumb)
                        .into(binding.randomMeal)
                   **/

                }else{
                    return
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                //Jika koneksi gagal

            }
        })
    }

    //52a. Membuat fungsi getPopularItems
    fun getPopularItems(){
        //Menggunakan retrofit instance untuk mendapatkan popular item
        RetrofitInstace.api.getPopularItems("Seafood").enqueue(object : Callback<CategoryList>{
            //override fungsi onResponse dan onFailure
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                //52c. set value ke live data
                if (response.body() != null){
                    popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {

            }

        })
    }

    //31. Membuat fungsi untuk membantu untuk listen ke randomMealLiveData di HomeMenuFragment
    fun observeRandomMealLivedata():LiveData<Meal>{
        return randomMealLiveData
    }

    //52d. Membuat fungsi untuk observe livedata getPopularItems
    fun observePopularItemsLivedata():LiveData<List<CategoryMeals>>{
        return popularItemsLiveData
    }

    //65c.Membuat fungsi untuk observe livedata favoritesMealLiveData
    fun observefavoriteMealsLiveData():LiveData<List<Meal>>{
        return favoritesMealsLiveData
    }
    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    //75a. Paste kode
    fun deleteMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

}