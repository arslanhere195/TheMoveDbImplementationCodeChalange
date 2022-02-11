package com.dev.androidcodechallange.dataClasses

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.dev.androidcodechallange.utils.Constants

data class MovieResponseDetails(
    val backdrop_path: String, val original_title: String, val overview: String,
    val poster_path: String, val tagline: String, val status: String,
    val release_date: String, val vote_average: Double, val vote_count: Int, val runtime: Int
) {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(
            view: ImageView,
            backdrop_path: String?
        ) { // This methods should not have any return type, = declaration would make it return that object declaration.

            if (!backdrop_path.isNullOrEmpty()) {

                Log.d("check", backdrop_path)
                Glide.with(view.context).load(Constants.BASE_IMAGE_URL.plus(backdrop_path))
                    .into(view)
            }
        }
    }
}
