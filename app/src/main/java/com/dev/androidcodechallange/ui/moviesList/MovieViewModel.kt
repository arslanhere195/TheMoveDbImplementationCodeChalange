package com.dev.androidcodechallange.ui.moviesList

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dev.androidcodechallange.api.ApiInterface
import com.dev.androidcodechallange.api.RetrofitBuilder
import com.dev.androidcodechallange.dataClasses.MovieResponse
import com.dev.androidcodechallange.repository.HomRepository
import com.dev.androidcodechallange.utils.Constants
import com.dev.androidcodechallange.utils.DataState
import com.dev.androidcodechallange.utils.StateResource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MovieViewModel constructor(
    application: Application,
) : AndroidViewModel(application) {

    private val context = application
    private val movies = MutableLiveData<MovieResponse>()
    private val moviesError = MutableLiveData<DataState<String>>()


    private var homeRepository =
        HomRepository(
            RetrofitBuilder.getRetrofit()!!.create(ApiInterface::class.java)
        )


    init {

        getMovies()
    }

    //getting Language
    fun getMovies() {
        viewModelScope.launch {

            homeRepository =
                HomRepository(
                    RetrofitBuilder.getRetrofit()!!
                        .create(ApiInterface::class.java)
                )
            homeRepository.getMovies("popular", Constants.API_KEY)
                .catch { e: Throwable ->
                    Log.d(TAG, "getmovies: error: ${e.message}")
                    if (e is HttpException) {
                        moviesError.postValue(
                            DataState.error(
                                response = StateResource.Response(
                                    message = e.message,
                                    errorCode = e.code(),
                                    responseType = StateResource.ResponseType.Toast()
                                )
                            )
                        )
                    } else {
                        moviesError.postValue(
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


                    movies.postValue(it)

                }
        }
    }

    fun getMoviesData(): LiveData<MovieResponse> {
        return movies;
    }

    fun getMoviesError(): LiveData<DataState<String>> {
        return moviesError;
    }


}