package com.dev.androidcodechallange.ui.movieDetails

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dev.androidcodechallange.api.ApiInterface
import com.dev.androidcodechallange.api.RetrofitBuilder
import com.dev.androidcodechallange.dataClasses.MovieResponseDetails
import com.dev.androidcodechallange.repository.MovieDetailsRepository
import com.dev.androidcodechallange.utils.Constants
import com.dev.androidcodechallange.utils.DataState
import com.dev.androidcodechallange.utils.StateResource
import retrofit2.HttpException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
class MovieDetailsViewModel constructor(
    application: Application,
) : AndroidViewModel(application) {

    private val movieDetails = MutableLiveData<MovieResponseDetails>()
    private val movieDetailsError = MutableLiveData<DataState<String>>()


    private var movieRepository =
        MovieDetailsRepository(
            RetrofitBuilder.getRetrofit()!!.create(ApiInterface::class.java)
        )



    fun getMovieDetails(id: String) {


        viewModelScope.launch {

            movieRepository =
                MovieDetailsRepository(
                    RetrofitBuilder.getRetrofit()!!
                        .create(ApiInterface::class.java)
                )
            movieRepository.getMovieDetails(id, Constants.API_KEY)
                .catch { e: Throwable ->
                    Log.d(ContentValues.TAG, "getmoviedetails: error: ${e.message}")
                    if (e is HttpException) {
                        movieDetailsError.postValue(
                            DataState.error(
                                response = StateResource.Response(
                                    message = e.message,
                                    errorCode = e.code(),
                                    responseType = StateResource.ResponseType.Toast()
                                )
                            )
                        )
                    } else {
                        movieDetailsError.postValue(
                            DataState.error(
                                response = StateResource.Response(
                                    message = e.message,
                                    errorCode = null,
                                    responseType = StateResource.ResponseType.Toast()
                                )
                            )
                        )
                    }
                }
                .collect {

                    movieDetails.postValue(it)

                }
        }
    }

    fun getMovieDetailsData(): LiveData<MovieResponseDetails> {
        return movieDetails;
    }

    fun getMovieDetailsError(): LiveData<DataState<String>> {
        return movieDetailsError;
    }
}