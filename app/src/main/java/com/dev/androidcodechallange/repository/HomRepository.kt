package com.dev.androidcodechallange.repository

import com.dev.androidcodechallange.api.ApiInterface
import com.dev.androidcodechallange.dataClasses.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomRepository constructor(private val apiInterface: ApiInterface) {


    fun getMovies(type: String, api: String): Flow<MovieResponse> = flow {
        emit(apiInterface.getMovies(type, api))
    }

}