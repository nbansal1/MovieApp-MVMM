package com.naman.movieapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.naman.movieapp.data.repository.MovieRepository
import com.naman.movieapp.data.response.MovieLists
import com.naman.movieapp.data.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieViewModel(val movieRepository: MovieRepository) : ViewModel() {

     fun getMovieDetails(movieName : String?) = GlobalScope.launch(Dispatchers.IO) {
        movieRepository.getMovieDetails(movieName)
    }

    fun getMovieList(movieName : String?) = GlobalScope.launch(Dispatchers.IO) {
        movieRepository.getMovieLists(movieName)
    }

    fun getMovieDetailsLiveData() : LiveData<MovieResponse> {
        return movieRepository.downloadedMovieResponse
    }

    fun getMovieListLiveData() : LiveData<MovieLists> {
        return movieRepository.downloadedMovieListResponse
    }
}