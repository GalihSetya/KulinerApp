package com.example.kulinerapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kulinerapp.MainActivity
import com.example.kulinerapp.DetailActivity
import com.example.kulinerapp.R
import com.example.kulinerapp.adapters.FavoritesMealsAdapter
import com.example.kulinerapp.databinding.FragmentFavoritesMenuBinding
import com.example.kulinerapp.viewModel.HomeviewModel
import com.google.android.material.snackbar.Snackbar

class favoritesMenuFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesMenuBinding
    //70a. Membuat Instance
    private lateinit var viewModel:HomeviewModel
    //73a. Membuat Instace untuk Recycleview adapter
    private lateinit var favoritesAdapter:FavoritesMealsAdapter

    companion object{
        //Membuat 3keys untuk intent ekstra
        const val MEAL_ID = "com.example.restomenu.fragments.idMeal"
        const val MEAL_NAME = "com.example.restomenu.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.restomenu.fragments.thumbMeal"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //70b. Paste dari HomeMenuFragment
        viewModel = (activity as MainActivity).viewModel

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites_menu, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //70b. Membuat Observer livedata, Lalu uji jalankan aplikasi
        observeFavorites()

        //73b.Membuat fungsi prepareRecycleView
        prepareRecycleView()

        //74a. Membuat fungsi untuk menghapus item di favorite
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            //74b. Override fungsi diatas
            //onMove return true
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            //onSwipe untuk menghapus recycleview ketika diswipe & undo fungsi hapus menggunakan snackbar
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedMeal = favoritesAdapter.differ.currentList[position]
                viewModel.deleteMeal(deletedMeal)

                Snackbar.make(requireView(),"Meal Deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(deletedMeal)
                    }
                ).show()
            }

        }
        //74c. panggil fungsi touchelper dan uji aplikasi
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)

        onFavoriteItemClick()
    }

    private fun onFavoriteItemClick() {
        favoritesAdapter.onfavItemClick = { itemFav ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(MEAL_ID,itemFav.idMeal)
            intent.putExtra(MEAL_NAME,itemFav.strMeal)
            intent.putExtra(MEAL_THUMB,itemFav.strMealThumb)
            startActivity(intent)
        }
    }

    //73c. Menerapkan Fungsi prepareRecycleView
    private fun prepareRecycleView() {
        //Menginisialisasi Adapter
        favoritesAdapter = FavoritesMealsAdapter()
        //Menggunakan binding untuk setup recycleview
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = favoritesAdapter
        }
    }

    //70a. Membuat Observer livedata
    private fun observeFavorites(){
        //73d. Ubah kode dibawah, kemudian uji aplikasi
        //viewModel.observefavoriteMealsLiveData().observe(viewLifecycleOwner, Observer { meals->
          //  meals.forEach{
            //    Log.d("test",it.idMeal)
           // }
       // })
        viewModel.observefavoriteMealsLiveData().observe(viewLifecycleOwner, Observer { meals->
            favoritesAdapter.differ.submitList((meals))
        })
    }

}