package com.example.kulinerapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kulinerapp.dataclass.Meal
import com.example.kulinerapp.database.MealDatabase
import com.example.kulinerapp.databinding.ActivityDetailBinding
import com.example.kulinerapp.fragments.HomeMenuFragment
import com.example.kulinerapp.viewModel.DetailViewModel
import com.example.kulinerapp.viewModel.DetailViewModelFactory


//36a. Membuat DetailActivity.kt
//buka activity_detail untuk mendesain layout
class DetailActivity : AppCompatActivity() {
    //41a. Membuat binding instance
    private lateinit var binding: ActivityDetailBinding

    //41d. Membuat 3 variabel
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String

    //44a. Membuat Instance dari MealViewModel
    private lateinit var DetailMvvM:DetailViewModel

    //46a. Membuat variabel untuk link ke youtube
    private lateinit var youtubeLink:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //41b. Menginisialisasi binding instance kemudian return root
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //44b. Menginisialisasi Instace lateinit var mealMvvmv
        //64a. Mengubah kode dibawah menjadi berikut
        //mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]
        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = DetailViewModelFactory(mealDatabase)
        DetailMvvM = ViewModelProvider(this,viewModelFactory)[DetailViewModel::class.java]

        //41c. Membuat fungsi untuk mendapatkan informasi dari randomMeal dari intent
        getMealInformationFromIntent()

        //41f. Membuat Fungsi untuk Menampilkan mealTumb ke Imageview dan mealName di toolbar
        setInformationInViews()
        //45c. Memanggil loading case ketika meminta request dari Api
        loadingCase()

        //44c. Menggunakan instance yang sudah dibuat
        DetailMvvM.getMealDetail(mealId)
        //44d. Membuat fungsi untuk mengobserve live data
        observerMealDetailsLiveData()

        //46c1. Membuat Fungsi untuk tombol Youtube
        onYoutubeImageClick()
        //64b. Membuat Fungsi klik favorite
       onFavoriteClick()
    }
    //64b. Membuat Fungsi klik favorite, setelah selesai uji jalankan aplikasi
    private fun onFavoriteClick() {
        binding.btnAddToFav.setOnClickListener{
            mealToSave?.let {
          DetailMvvM.insertMeal(it)
                Toast.makeText(this, "Makanan Tersimpan", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //46c2. Membuat Fungsi untuk tombol Youtube Kemudian uji jalankan aplikasi
    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }
    //64b. Membuat Fungsi klik favorite
    private var mealToSave:Meal?=null
    //44d. Membuat fungsi untuk mengobserve live data
    private fun observerMealDetailsLiveData() {
        DetailMvvM.observerMealDetailsLiveData().observe(this,object :Observer<Meal>{
            //44c. Mengoverride method onChaged, disini untuk set category dan area pada layout, Kemudian uji jalankan aplikasi
            override fun onChanged(t: Meal?) {

                //45d.Ketika mendapat respon dari Api Kemudian uji jalankan Aplikasi
                onResponseCase()

                val meal = t
                //64c. Assign Fungsi
                mealToSave = meal

                binding.tvCategory.text = "Category : ${meal!!.strCategory}"
                binding.tvArea.text = "Area : ${meal.strArea}"
                binding.tvInstructionsSteps.text = meal.strInstructions

                //46b. Menerapkan variabel youtube
                youtubeLink = meal.strYoutube.toString()

            }
        })
    }


    //41f1. Menampilkan mealTumb ke Imageview dan mealName di toolbar, Kemudian uji jalankan Aplikasi
    // Membuat fungsi untuk Menampilkan informasi diViews
    private fun setInformationInViews() {
        //Menampilkan image menggunakan Glide
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        //Menampilkan Nama di toolbar
        binding.collapsingToolbar.title = mealName
        //Membuat warna titile menjadi putih
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    //41c1. Membuat fungsi untuk mendapatkan informasi dari randomMeal dari intent
    private fun getMealInformationFromIntent() {
        val intent = intent
        //41e. Mengassign variable 41d.
        mealId = intent.getStringExtra(HomeMenuFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeMenuFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeMenuFragment.MEAL_THUMB)!!
    }

    //45a. Membuat Fungsi untuk menyembunyikan semua views kecuali image view dan title dan Menampilkan progressbar
    // dan mendapatkan respon dari Api kemudian menampilkannya kembali
    //45b. Menyembunyikan floating action button dan category, area, dan instructions textview
    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}
