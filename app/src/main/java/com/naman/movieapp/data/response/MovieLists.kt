package com.naman.movieapp.data.response


import com.google.gson.annotations.SerializedName

data class MovieLists(
    @SerializedName("Search")
    val search: List<Search>
)