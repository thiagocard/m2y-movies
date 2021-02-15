package br.com.mobile2you.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.com.mobile2you.domain.repository.MovieRepository
import kotlinx.coroutines.flow.*

class MovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val movieId = MutableSharedFlow<String?>(replay = 1)
    private val favorite = MutableStateFlow(false)

    val movie = movieId.filterNotNull().map { id ->
        movieRepository.getMovieDetails(id)
    }.combine(favorite) { movie, isFavorite ->
        movie.copy(isFavorite = isFavorite)
    }.map {
        Result.success(it)
    }.catch { throwable ->
        emit(Result.failure(throwable))
    }.asLiveData(viewModelScope.coroutineContext)

    val similarMovies = movieId.filterNotNull().map { id ->
        movieRepository.getSimilarMovies(id)
    }.map {
        Result.success(it)
    }.catch { throwable ->
        emit(Result.failure(throwable))
    }.asLiveData(viewModelScope.coroutineContext)

    fun setMovieId(id: String) {
        movieId.tryEmit(id)
    }

    fun toggleFavorite() {
        favorite.value = !favorite.value
    }

}