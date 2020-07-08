package com.naman.movieapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naman.movieapp.R
import com.naman.movieapp.data.response.Search
import com.naman.movieapp.ui.callbacks.ICallback
import kotlinx.android.synthetic.main.movie_item_layout.view.*

private lateinit var context: Context
private lateinit var movieList: List<Search>

class MovieListAdapter(val context: Context, var movieList: List<Search>, val callback:ICallback): RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {
    inner class MovieListViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener{
                val cityName = itemView.tv_title.text.toString()
                callback.onCallback(cityName)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.movie_item_layout,parent,false)
        return MovieListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movieDetails:Search = movieList[position]
        Glide.with(context)
            .load(movieDetails.poster)
            .into(holder.itemView.iv_poster)

        holder.itemView.tv_title.text = movieDetails.title
    }
}