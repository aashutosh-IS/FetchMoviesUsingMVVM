package com.example.moviemvvm.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviemvvm.data.vo.MovieDetails
import com.oxcoding.moviemvvm.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SMVM(private val movieRepo:MovieDetailRepo,movieId:Int): ViewModel() {

    private val compositeDisposable=CompositeDisposable()

    val movieDetails:LiveData<MovieDetails> by lazy {
        movieRepo.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState:LiveData<NetworkState> by lazy {
        movieRepo.getMovieDetailsNetworkState()

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}