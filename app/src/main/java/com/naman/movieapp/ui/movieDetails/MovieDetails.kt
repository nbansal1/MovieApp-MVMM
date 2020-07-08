package com.naman.movieapp.ui.movieDetails

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.naman.movieapp.R
import com.naman.movieapp.data.response.MovieResponse
import com.naman.movieapp.ui.viewModel.MovieViewModel
import com.naman.movieapp.ui.viewModel.MovieViewModelFactory
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MovieDetails : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    val viewModelFactory: MovieViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val cityName = handleIntent()

        supportActionBar?.title = cityName

        val movieDetailsViewModel = ViewModelProviders.of(this,viewModelFactory).get(
            MovieViewModel::class.java)

        movieDetailsViewModel.getMovieDetailsLiveData().observe(this, Observer {
            initUI(it)
        })

        movieDetailsViewModel.getMovieDetails(cityName)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    @SuppressLint("SetTextI18n")
    private fun initUI(movieResponse: MovieResponse) {

        Glide.with(this)
            .load(movieResponse.poster)
            .into(iv_poster)

        tv_title.text = movieResponse.title
        tv_year.text = movieResponse.year
        tv_category.text = movieResponse.genre
        tv_duration.text = movieResponse.runtime
        tv_rating.text = " \u2605 ${movieResponse.imdbRating}"
        tv_plot.text = movieResponse.plot
        tv_director.text = " Director - ${movieResponse.director}"
        tv_writer.text = " Writer - ${movieResponse.writer}"
        tv_actor.text = " Actors - ${movieResponse.actors}"
    }

    private fun handleIntent() : String {
        val intent = intent
        return intent.getStringExtra("MOVIE_NAME")
    }
}
