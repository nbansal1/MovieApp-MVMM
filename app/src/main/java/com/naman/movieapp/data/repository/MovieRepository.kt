package com.naman.movieapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naman.movieapp.data.network.MovieAPIInterface
import com.naman.movieapp.data.response.MovieLists
import com.naman.movieapp.data.response.MovieResponse
import java.lang.Exception

class MovieRepository( val apiSource : MovieAPIInterface) {

    private val _networkState =  MutableLiveData<NetworkState>()
    val networkState : LiveData<NetworkState>
        get() = _networkState

    private val _downloadedMovieResponse =  MutableLiveData<MovieResponse>()
    val downloadedMovieResponse : LiveData<MovieResponse>
        get() = _downloadedMovieResponse

    private val _downloadedMovieListResponse =  MutableLiveData<MovieLists>()
    val downloadedMovieListResponse : LiveData<MovieLists>
        get() = _downloadedMovieListResponse

    suspend fun getMovieDetails(movieName : String?) {
        _networkState.postValue(NetworkState.LOADING)
        try {
            val movieResponse =  apiSource.getMovieDetails(movieName).await()
            _downloadedMovieResponse.postValue(movieResponse)
            _networkState.postValue(NetworkState.LOADED)
        } catch (e : Exception) {
            Log.e("ERROR", "", e)
            _networkState.postValue(NetworkState.ERROR)
        }
    }

    suspend fun getMovieLists(movieName : String?) {
        if(movieName?.length!! < 3) {
            _networkState.postValue(NetworkState.LOADING)
            return
        }
        _networkState.postValue(NetworkState.LOADING)
        try {
            val movieResponse =  apiSource.getMovieList(movieName).await()
            _downloadedMovieListResponse.postValue(movieResponse)
            _networkState.postValue(NetworkState.LOADED)
        } catch (e : Exception) {
            Log.e("ERROR", "", e)
            _networkState.postValue(NetworkState.ERROR)
        }
    }
}