package com.naman.movieapp.data.response


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Actors")
    val actors: String,
    @SerializedName("Director")
    val director: String,
    @SerializedName("Genre")
    val genre: String,
    val imdbID: String,
    val imdbRating: String,
    val imdbVotes: String,
    @SerializedName("Plot")
    val plot: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Production")
    val production: String,
    @SerializedName("Rated")
    val rated: String,
    @SerializedName("Ratings")
    val ratings: List<Rating>,
    @SerializedName("Response")
    val response: String,
    @SerializedName("Runtime")
    val runtime: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Writer")
    val writer: String,
    @SerializedName("Year")
    val year: String
)