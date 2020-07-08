package com.naman.movieapp.ui.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.naman.movieapp.R
import com.naman.movieapp.ui.adapter.MovieListAdapter
import com.naman.movieapp.ui.callbacks.ICallback
import com.naman.movieapp.ui.movieDetails.MovieDetails
import com.naman.movieapp.ui.viewModel.MovieViewModel
import com.naman.movieapp.ui.viewModel.MovieViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MoviesActivity : AppCompatActivity(), ICallback, KodeinAware {

    override val kodein: Kodein by closestKodein()

    val viewModelFactory: MovieViewModelFactory by instance()
    lateinit var movieListAdapter: MovieListAdapter

    lateinit var  movieViewModel:MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSearchView()

        movieViewModel = ViewModelProviders.of(this,viewModelFactory).get(MovieViewModel::class.java)

        movieViewModel.getMovieListLiveData().observe(this, Observer {
            if(it.search != null) {
                movieListAdapter.movieList = it.search
                movieListAdapter.notifyDataSetChanged()
            }
        })

        setAdapter()

//        btn.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this,
//                MovieDetails::class.java)
//            intent.putExtra("MOVIE_NAME", "Inception")
//            startActivity(intent)
//        })n


    }

    private fun setAdapter() {
        movieListAdapter = MovieListAdapter(this, listOf(),this)
        recycler.layoutManager = GridLayoutManager(this,2)
        recycler.adapter = movieListAdapter
    }

    private fun setSearchView() {
        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MoviesActivity, "$query", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if(newText?.length!! > 3)
                movieViewModel.getMovieList(newText)
                return true
            }
        })
    }

    override fun onCallback(movieName: String) {
        val intent = Intent(this,
                MovieDetails::class.java)
            intent.putExtra("MOVIE_NAME", movieName)
            startActivity(intent)
    }
}
