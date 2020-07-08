package com.naman.movieapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naman.movieapp.data.repository.MovieRepository

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(val movieRepository: MovieRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(
            movieRepository
        ) as T
    }
}