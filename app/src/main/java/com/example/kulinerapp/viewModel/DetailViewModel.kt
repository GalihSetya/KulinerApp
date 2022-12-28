package com.example.kulinerapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kulinerapp.dataclass.Meal
import com.example.kulinerapp.dataclass.MealList
import com.example.kulinerapp.database.MealDatabase
import com.example.kulinerapp.network.RetrofitInstace
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//43a. Membuat MealViewModel
// Menginherit dari viewModel class
//62a. Membuat val untuk Instance database
class DetailViewModel(
   val mealDatabase:MealDatabase
):ViewModel() {

    //43b. Membuat var live data
    private var mealDetailsLiveData = MutableLiveData<Meal>()

    //43c. Membuat fungsi untuk mendapatkan randomMeal dan meletakkan di LiveData
    fun getMealDetail(id:String){
        RetrofitInstace.api.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }
                else{
                    return
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {

            }

        })
    }
    //43d. Membuat Fungsi untuk listen livedata ini dari luar
    fun observerMealDetailsLiveData():LiveData<Meal>{
        return mealDetailsLiveData
    }
    //62b. Membuat Fungsi untuk Insert
    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    //62c. Membuat Fungsi Delete
    //75a. Cut kode dibawah ke homeviewModel
//    fun deleteMeal(meal:Meal){
  //      viewModelScope.launch {
    //       mealDatabase.mealDao().delete(meal)
     //   }
   // }


}