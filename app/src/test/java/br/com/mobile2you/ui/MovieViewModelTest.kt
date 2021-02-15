package br.com.mobile2you.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.mobile2you.domain.model.Movie
import br.com.mobile2you.domain.model.SimilarMovie
import br.com.mobile2you.domain.repository.MovieRepository
import br.com.mobile2you.test.CoroutineTestRule
import com.nhaarman.mockitokotlin2.*
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest : TestCase() {

    private val movieRepo = mock<MovieRepository>()
    private val viewModel by lazy { MovieViewModel(movieRepo) }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `Should get movie with success`() = runBlocking {
        val movie = movie()
        whenever(movieRepo.getMovieDetails(any())).thenReturn(movie)

        viewModel.setMovieId("999")

        val observer = spy<Observer<Result<Movie>?>>()
        viewModel.movie.observeForever(observer)

        verify(observer).onChanged(eq(Result.success(movie)))
    }

    private fun movie() = Movie(
        title = "Godzilla vs Kong",
        poster = "",
        backdrop = "",
        likeCount = "512k likes",
        views = "50.230 views",
        isFavorite = false
    )

    @Test
    fun `Should get similar movies with success`() = runBlocking {
        val similarMovies = listOf(
            SimilarMovie(title = "Batman 1", yearAndGenres = "", poster = ""),
            SimilarMovie(title = "Batman 2", yearAndGenres = "", poster = ""),
            SimilarMovie(title = "Batman 3", yearAndGenres = "", poster = ""),
            SimilarMovie(title = "Batman 4", yearAndGenres = "", poster = ""),
        )
        whenever(movieRepo.getSimilarMovies(any())).thenReturn(similarMovies)

        viewModel.setMovieId("999")

        val observer = spy<Observer<Result<List<SimilarMovie>>>>()
        viewModel.similarMovies.observeForever(observer)

        verify(observer).onChanged(eq(Result.success(similarMovies)))
    }

    @Test
    fun `Should switch favorite state between true and false with success`() = runBlocking {
        val movie = movie()
        whenever(movieRepo.getMovieDetails(any())).thenReturn(movie)

        viewModel.setMovieId("999")

        val observer = spy<Observer<Result<Movie?>>>()
        viewModel.movie.observeForever(observer)

        // Toggle favorite (from false to true)
        viewModel.toggleFavorite()

        inOrder(observer) {
            verify(observer).onChanged(eq(Result.success(movie)))
            verify(observer).onChanged(eq(Result.success(movie.copy(isFavorite = true))))
        }
    }

}