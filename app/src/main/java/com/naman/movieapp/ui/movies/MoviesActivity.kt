package com.naman.movieapp.ui.movies

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.naman.movieapp.R
import com.naman.movieapp.data.repository.NetworkState
import com.naman.movieapp.data.repository.NetworkState.Companion.LOADED
import com.naman.movieapp.ui.adapter.MovieListAdapter
import com.naman.movieapp.ui.callbacks.ICallback
import com.naman.movieapp.ui.movieDetails.MovieDetails
import com.naman.movieapp.ui.viewModel.MovieViewModel
import com.naman.movieapp.ui.viewModel.MovieViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.network_state_layout.*
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

        movieViewModel.getNetworkState().observe(this, Observer {
            checkNetworkState(it)
        })

        movieViewModel.getMovieListLiveData().observe(this, Observer {
            if(it.search != null) {
                movieListAdapter.movieList = it.search
                movieListAdapter.notifyDataSetChanged()
            }
        })

        setAdapter()
    }

    @SuppressLint("SetTextI18n")
    private fun checkNetworkState(it: NetworkState) {
        when {
            it.status.name.equals("RUNNING") -> {
                networkStateLayout.visibility = View.VISIBLE
                llProgressView.visibility = View.VISIBLE
                recycler.visibility = View.GONE
                tvNetworkMsg.text = "Loading"
            }
            it.status.name.equals("SUCCESS") -> {
                networkStateLayout.visibility = View.GONE
                llProgressView.visibility = View.GONE
                recycler.visibility = View.VISIBLE
            }
            else -> {
                networkStateLayout.visibility = View.VISIBLE
                llProgressView.visibility = View.GONE
                llErrorMsg.visibility = View.VISIBLE
                recycler.visibility = View.GONE

            }
        }
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
                if(newText?.isEmpty()!!){
                    networkStateLayout.visibility = View.GONE
                } else {
                    movieViewModel.getMovieList(newText)
                }
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
