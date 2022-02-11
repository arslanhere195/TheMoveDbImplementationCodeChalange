package com.dev.androidcodechallange.dataClasses

import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.dev.androidcodechallange.R
import com.dev.androidcodechallange.utils.Constants
import java.util.*
import kotlin.collections.ArrayList

data class Movie(
    val poster_path: String? = null,
    val original_title: String? = null,
    val release_date: String? = null,
    val id: Int,
    val vote_average: Float
) {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, poster_path: String?) {
            if (!poster_path.isNullOrEmpty()) {
                Glide.with(view.context).load(Constants.BASE_IMAGE_URL.plus(poster_path)).into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("color")
        fun setColor(textView: TextView, movie_year: String?) {


            val c: Calendar = Calendar.getInstance()
            val year: Int = c.get(Calendar.YEAR)
            val releaseDate: ArrayList<String> = movie_year?.split('-') as ArrayList<String>
            if (year.toString() == releaseDate[0]) {
                textView.text = releaseDate[0]
                textView.setTypeface(null, Typeface.BOLD)
                textView.context?.resources?.getColor(R.color.red)
                    ?.let { textView.setTextColor(it) };
            } else {
                textView.setTypeface(null, Typeface.NORMAL)
                textView.text = releaseDate[0]
                textView.context?.resources?.getColor(R.color.black)
                    ?.let { textView.setTextColor(it) };
            }

        }
    }


}


