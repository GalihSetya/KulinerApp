package com.example.kulinerapp.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters

//60a. Membuat Kotlin Class/File MealTypeConvertor
//60b. Menganotasi menggunakan @TypeConverters
@TypeConverters
class MealTypeConvertor {
    //60c. Membuat Fungsi untuk mengkonversi data type a ke data type b
    //Menganotasi Menggunakan @TypeConverter
    @TypeConverter
    fun fromAnyToString(attribute:Any?) : String{
        if(attribute == null)
            return ""
        return attribute as String
    }

    @TypeConverter
    fun fromStringToAny(attribute: String?) : Any{
        if(attribute == null)
            return ""
        return attribute
    }
}
