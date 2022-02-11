package com.dev.androidcodechallange.api

import com.dev.androidcodechallange.dataClasses.MovieResponse
import com.dev.androidcodechallange.dataClasses.MovieResponseDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {


    @GET(ApiEndPoints.GET_MOVIES)
    suspend fun getMovies(
        @Path("type") type: String,
        @Query("api_key") api_key: String
    ): MovieResponse


    @GET(ApiEndPoints.GET_MOVIE_Details)
    suspend fun getMovieDetails(
        @Path("id") id: String,
        @Query("api_key") api_key: String
    ): MovieResponseDetails

}