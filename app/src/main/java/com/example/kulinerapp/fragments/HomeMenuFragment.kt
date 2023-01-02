package com.example.kulinerapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kulinerapp.DetailActivity
import com.example.kulinerapp.MainActivity
import com.example.kulinerapp.R
import com.example.kulinerapp.adapters.MenuPopularAdapter
import com.example.kulinerapp.databinding.FragmentHomeMenuBinding
import com.example.kulinerapp.dataclass.CategoryMeals
import com.example.kulinerapp.dataclass.Meal
import com.example.kulinerapp.viewModel.HomeviewModel


class HomeMenuFragment : Fragment() {

    //20. Membuat var Instance untuk viewBinding
    private lateinit var binding: FragmentHomeMenuBinding
    //32a. Membuat var untuk Instance viewModel
    private lateinit var viewHomeModel:HomeviewModel
    //32b. Membuat lateinin ramdommeal
    private lateinit var randomMeal:Meal
    //53d. Membuat private late init untuk set adapter
    private lateinit var popularItemsAdapter: MenuPopularAdapter


    //38. Membuat companion Object
    companion object{
        //Membuat 3keys untuk intent ekstra
        const val MEAL_ID = "com.example.restomenu.fragments.idMeal"
        const val MEAL_NAME = "com.example.restomenu.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.restomenu.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //69a. Membuat ViewModel, uji jalankan aplikasi
        viewHomeModel = (activity as MainActivity).viewModel
        super.onCreate(savedInstanceState)
        //33. membuat Instance untuk ViewModel
        //67a. Menghapus Fungsi dibawah
        //viewHomeModel = ViewModelProviders.of(this)[HomeviewModel::class.java]
        //53e. Menginisialisasi lateinit var popularitemsAdapter
        popularItemsAdapter = MenuPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //21. Inflate  layout untuk fragment ini
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_menu ,container, false)
        return binding.root
    }

    //17. Membuat fungsi OnViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //membuat binding viewHomeModel
        binding.viewHomeModel = viewHomeModel
        binding.lifecycleOwner = viewLifecycleOwner
        //18. Menggunakan Retrofit instance untuk mendapatkan data RadomMeal
        // RandomMeal Return call, menggunakan enqueue, dan berikan callback object

        //27b. Cut kode dibawah dan paste ke HomeviewModel
        /**
        RetrofitInstace.api.getRandomMeal().enqueue(object : Callback<MealList>{
         **/
            //19. Ctrl i dan ovveride 2 fungsi Callback
        /**
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                //Jika terkoneksi
                if(response.body() !=null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    //untuk mengetes apakah koneksi berhasil gunakan log dibawah, kemudian jalankan aplikasi kemudian lihat di logcat dengan filter test
                    //Log.d("TEST","meal id ${randomMeal.idMeal} name ${randomMeal.strMeal}")
                     **/
                    //22. Menggunakan glide untuk mendapatkan gambar dari internet dan menampilkan ke ImageView dan test aplikasi
                    /**
                    Glide.with(this@HomeMenuFragment)
                        .load(randomMeal.strMealThumb)
                        .into(binding.randomMeal)

                }else{
                    return
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                //Jika koneksi gagal
            }
        })
        **/
        //53c1. Membuat fungsi preparePopularItemsRecyclerview
        preparePopularItemsRecyclerview()

        //34. Menggunakan Instance yang sudah dibuat
        //Klik alt & enter untuk membuat fungsi observerRandomMeal()
       // viewModel.getRadomMeal()
        observerRandomMeal()
        //37a. Membuat Fungsi onRandomMealClick
        onRandomMealClick()

        //53a. Memanggil method untuk mendapatkan popular Item
        viewHomeModel.getPopularItems()
        observerPopularItemsLivedata()

        //55a. Membuat fungsi click listener popularitems recyclerview untuk menampilkan detail
        onPopularItemClick()
    }
        //55b. Menerapkan Fungsi Click Listener, Kemudian Uji Jalankan aplikasi
    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity,DetailActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    //53c2. set mealList dibawah kedalam adapter
    private fun preparePopularItemsRecyclerview() {
        binding.recViewMealPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            //53f. Set Adapter
            adapter = popularItemsAdapter
        }
    }

    //53b. Membuat Fungsi observerPopularItemsLivedata()
    private fun observerPopularItemsLivedata() {
        viewHomeModel.observePopularItemsLivedata().observe(viewLifecycleOwner,
            //implement object members
            //53c1. set mealList dibawah kedalam adapter, Kemudian uji jalankan apikasi
         { mealList ->
            popularItemsAdapter.setMeals(menufavList = mealList as ArrayList<CategoryMeals>)
        })

    }

    private fun onRandomMealClick() {
        //37b. Set click listener di randomMeal
        binding.randomMealCard.setOnClickListener{
            //37c. Membuat Intent dari activity ini ke DetailActivity
            val intent = Intent(activity,DetailActivity::class.java)
            //40. Menambahkan extras
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            //37d. Jalankan Intent Kemudian Test Aplikasi
            startActivity(intent)
        }

    }


    private fun observerRandomMeal() {
        //35. untuk Listen ke RandomMeal live data kemudian uji jalankan aplikasi
        /**
        viewHomeModel.observeRandomMealLivedata().observe(viewLifecycleOwner,object : Observer<Meal>{
            //implement method onChaged
            override fun onChanged(t: Meal?) {
                Glide.with(this@HomeMenuFragment)
                        //!! artinya notnull
                    .load(t!!.strMealThumb)
                    .into(binding.randomMeal)
            }
        **/

            //39. Mengganti kode diatas menjadi seperti berikut
        viewHomeModel.observeRandomMealLivedata().observe(viewLifecycleOwner
        ) { meal ->
            //implement method onChaged

// 78. Menghapus fungsi glide dibawah kemudian ujicoba aplikasi
            /**
            Glide.with(this@HomeMenuFragment)
                //!! artinya notnull
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)
             **/
            this.randomMeal = meal
        }

    }
}

