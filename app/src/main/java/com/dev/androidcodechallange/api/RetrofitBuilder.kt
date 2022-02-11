package com.dev.androidcodechallange.api

import android.app.Application


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitBuilder(
    private val application: Application,
) {


    companion object {
        private var retrofit: Retrofit? = null
        private const val BASE_URL = "https://api.themoviedb.org"

        fun getRetrofit(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient.Builder().build())
                    .build()
            }
            return retrofit
        }
    }
}