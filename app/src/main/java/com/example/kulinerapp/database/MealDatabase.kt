package com.example.kulinerapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.*
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kulinerapp.dataclass.Meal

//59a. Membuat class Builder
//59b. Mengubah menjadi abstract class, untuk set instance dari interface
//59c. Menganotasi menggunakan database
@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConvertor::class)
abstract class MealDatabase : RoomDatabase(){

    //59d. Membuat Instace menggunakan fungsi abstract
    abstract fun mealDao(): MealDao

    //59e. Membuat fungsi untuk mereturn instace dari database class
    //Menganotasi menggunakan @Volatile
    companion object {
        @Volatile
        var INSTANCE: MealDatabase? = null

    //59f. Membuat fungsi untuk mendapatkan instance dari database
    //59g. Menganotasi Fungsi menggunakan @Synchronized
        @Synchronized
        fun getInstance(context: Context):MealDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDatabase
        }
    }

}