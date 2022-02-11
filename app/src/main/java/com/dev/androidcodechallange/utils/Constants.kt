package com.dev.androidcodechallange.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.dev.androidcodechallange.BuildConfig
import com.dev.androidcodechallange.R
import com.kaopiz.kprogresshud.KProgressHUD
import com.shashank.sony.fancytoastlib.FancyToast

class Constants {

    companion object {

        const val BASE_URL = "https://api.themoviedb.org/3/"

        const val API_PARAM = "api_key"
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w300"


        fun showError(context: Context, message: String) {
            FancyToast.makeText(
                context,
                message,
                FancyToast.LENGTH_SHORT,
                FancyToast.ERROR,
                false
            ).show()
        }


        fun setProgressDialog(context: Context): KProgressHUD {
            return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setBackgroundColor(ContextCompat.getColor(context, R.color.black))
                .setCancellable(false)
                .setAnimationSpeed(1)
        }
    }

}