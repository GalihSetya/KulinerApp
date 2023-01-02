package com.example.kulinerapp

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load

//76a. Membuat file Binding Adapter untuk mencoba data binding ke layout
//76b. Memanggil @BindingAdapter
@BindingAdapter("UrlGambar")
//76c. Buat fungsi untuk Binding Adapter menggunakan library coil yang sudah ditambahakan
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUrl = imgUrl.toUri().buildUpon().scheme("https").build()
        //Menggunakan coil load
        imgView.load(imgUrl) {
            //tambahkan tambahkan animasi saat mengambil gambar dari internet
            //animasi mengambil template dari codelab amphibians, copy paste ke drawable
            //kemudian menuju ke layout fragment_home_menu
            placeholder(R.drawable.loading_animation)
            error(R.drawable.error)
        }
    }
}
