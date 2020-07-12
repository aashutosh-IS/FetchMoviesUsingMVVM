package com.example.moviemvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviemvvm.data.vo.Movie
import com.oxcoding.moviemvvm.data.api.POST_PER_PAGE
import com.oxcoding.moviemvvm.data.api.TheMovieDBInterface
import com.oxcoding.moviemvvm.data.repository.MovieDataSource
import com.oxcoding.moviemvvm.data.repository.MovieDataSourceFactory
import com.oxcoding.moviemvvm.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviePageListRepository(private val apiService: TheMovieDBInterface) {

    lateinit var moiePageList: LiveData<PagedList<Movie>>
    lateinit var moviesDatSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        moviesDatSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val confg = PagedList.Config.Builder()
            .setEnablePlaceholders(false).setPageSize(POST_PER_PAGE)
            .build()

        moiePageList = LivePagedListBuilder(moviesDatSourceFactory, confg).build()
        return moiePageList
    }

    fun getNetworkSate(): LiveData<NetworkState> {
return Transformations.switchMap<MovieDataSource,NetworkState>(
    moviesDatSourceFactory.moviesLiveDataSource,MovieDataSource::networkState)

    }
}