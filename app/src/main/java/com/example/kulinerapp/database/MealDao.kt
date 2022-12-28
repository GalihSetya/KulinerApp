package com.example.kulinerapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kulinerapp.dataclass.Meal

//58a. Membuat interface Dao
//58b. Meanotasi menggunakan dellanotation yang memberi tau room bahwa ini adalah dell interface
@Dao
interface MealDao {
    //58c. Membuat fungsi Insert menggunakan fungsi suspend agar tidak mengeblok main thread
    //Menggunakan onConflictStrategy untuk menggantikan fungsi Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal:Meal)

    //58d. Membuat fungsi Delete
    @Delete
    suspend fun delete(meal: Meal)

    //58e. Membuat fungsi Query yang akan mereturn live data
    @Query("SELECT * FROM mealInformation")
    fun getAllMeals():LiveData<List<Meal>>

}