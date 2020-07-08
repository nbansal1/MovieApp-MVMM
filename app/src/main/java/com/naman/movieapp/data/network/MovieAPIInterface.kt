package com.naman.movieapp.data.network

import com.naman.movieapp.data.response.MovieLists
import com.naman.movieapp.data.response.MovieResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

//http://www.omdbapi.com/?apikey=b9bd48a6&t=kite

interface MovieAPIInterface {

    @GET(".")
    fun getMovieDetails(
        @Query("t") movieName: String?
    ) : Deferred<MovieResponse>

    @GET(".")
    fun getMovieList(
        @Query("s") movieName: String?
    ) : Deferred<MovieLists>
}