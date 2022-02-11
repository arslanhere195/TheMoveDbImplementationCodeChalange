package com.dev.androidcodechallange.repository

import com.dev.androidcodechallange.api.ApiInterface
import com.dev.androidcodechallange.dataClasses.MovieResponseDetails
 import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieDetailsRepository constructor(private val apiInterface: ApiInterface) {

    fun getMovieDetails(id: String, api: String): Flow<MovieResponseDetails> = flow {
        emit(apiInterface.getMovieDetails(id, api))
    }
}