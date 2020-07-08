package com.naman.movieapp

import android.app.Application
import com.naman.movieapp.data.network.MovieClient
import com.naman.movieapp.data.repository.MovieRepository
import com.naman.movieapp.ui.viewModel.MovieViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MovieApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidModule(this@MovieApplication))
        bind() from singleton { MovieClient.invoke() }
        bind() from singleton { MovieRepository(instance()) }
        bind() from singleton {
            MovieViewModelFactory(
                instance()
            )
        }

    }
    override fun onCreate() {
        super.onCreate()
    }
}