package com.example.kulinerapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kulinerapp.databinding.PopularItemsBinding
import com.example.kulinerapp.dataclass.CategoryMeals

//48a. Membuat kotlin class/file MenuPopularAdapter untuk recycler view
//48b. Inherit dari RecyclerView adapter
class MenuPopularAdapter:
    RecyclerView.Adapter<MenuPopularAdapter.PopularMealViewHolder>() {

    //54a. Membuat fungsi click listener popularitems recyclerview untuk menampilkan detail
    lateinit var onItemClick:((CategoryMeals) -> Unit)

    //50a. Membuat list categoryMeals
    private var menufavList = ArrayList<CategoryMeals>()
    //50b. Membuat publik method untuk set menufavList dari luar class
    fun setMeals(menufavList:ArrayList<CategoryMeals>){
        this.menufavList = menufavList
        notifyDataSetChanged()
    }

    //48d. Mengimplement fungsi recyclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        //50c. Membuat implement untuk return intance dari PopularMealViewHolder class
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        //50d. Menggunakan Glide untuk menampilkan gambar
        Glide.with(holder.itemView)
            .load(menufavList[position].strMealThumb)
            .into(holder.binding.imgPopularMenu)

        //binding ke id faveMenu untuk menampilkan nama Menu
        holder.binding.faveMenu.text = menufavList[position].strMeal
        //54b. Membuat fungsi click listener popularitems recyclerview untuk menampilkan detail
        holder.itemView.setOnClickListener{
            onItemClick.invoke(menufavList[position])
        }
    }

    override fun getItemCount(): Int {
        //50e. return size menufavList
        return menufavList.size
    }

    //48c. Membuat class ViewHolder, ini menginherit dari recyclerview viewholder
    //Membuat parameters di constructor menggunakan viewbinding
    class PopularMealViewHolder(val binding:PopularItemsBinding): RecyclerView.ViewHolder(binding.root)
}