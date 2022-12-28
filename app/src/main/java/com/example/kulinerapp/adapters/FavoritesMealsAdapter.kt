package com.example.kulinerapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kulinerapp.databinding.MealItemBinding
import com.example.kulinerapp.dataclass.CategoryMeals
import com.example.kulinerapp.dataclass.Meal
import com.example.kulinerapp.dataclass.MealList

//72a. Membuat adapter untuk Recycleview Favorite Menu
class FavoritesMealsAdapter : RecyclerView.Adapter<FavoritesMealsAdapter.FavoritesMealsAdapterViewHolder>() {

    inner class FavoritesMealsAdapterViewHolder(val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)
    lateinit var onfavItemClick: ((Meal) -> Unit)

    //72b. Menggunakan diffutil untuk meanbah performa dari recycleview
    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        //72c. Override method untuk diffutil, untuk membandingakan premarykey diantara item
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }
        //72c. Override method untuk diffutil, untuk membandingakan semua objek
        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    //72d. Membuat value untuk set list atau untuk mendapatkan item dari list
    val differ = AsyncListDiffer(this,diffUtil)
    //72e. Menimplementasi fungsi recycleview
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesMealsAdapterViewHolder {
        //72f. return instance dari favorite viewholder
        return FavoritesMealsAdapterViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }
    //72e. Buat fungsi untuk menampilkan ke layout
    override fun onBindViewHolder(holder: FavoritesMealsAdapterViewHolder, position: Int) {
        // buat val untuk mendapatkan informasi menggunakan differ
        val meal = differ.currentList[position]
        //menggunakan glide untuk menampilkan gambar ke imageView
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)
        //membuat binding untuk menampilkan text di recycleview
        holder.binding.tvMealName.text = meal.strMeal
        //
        holder.itemView.setOnClickListener{
            onfavItemClick.invoke(differ.currentList[position])
        }
    }
    //72e. Buat fungsi untuk mereturn size
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}