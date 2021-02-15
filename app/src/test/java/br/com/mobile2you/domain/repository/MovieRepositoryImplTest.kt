package br.com.mobile2you.domain.repository

import br.com.mobile2you.data.mappers.Mapper
import br.com.mobile2you.data.model.*
import br.com.mobile2you.data.service.GenresService
import br.com.mobile2you.data.service.MoviesService
import br.com.mobile2you.di.mappersModule
import br.com.mobile2you.domain.model.Movie
import br.com.mobile2you.domain.model.SimilarMovie
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject
import org.threeten.bp.LocalDate
import retrofit2.Response

class MovieRepositoryImplTest : KoinTest {

    init {
        startKoin { modules(mappersModule) }
    }

    private val moviesService = mock<MoviesService>()
    private val genresService = mock<GenresService>()
    private val movieMapper: Mapper<TmdbMovie, Movie> by inject(qualifier = named("movieMapper"))
    private val similarMovieMapper: Mapper<TmdbSimilarMovie, SimilarMovie> by inject(
        qualifier = named("similarMovieMapper")
    )

    private val repo by lazy {
        MovieRepositoryImpl(moviesService, genresService, movieMapper, similarMovieMapper)
    }

    @After
    fun tearDown() = stopKoin()

    @Test
    fun `Should get movie details with success`() = runBlocking {
        val tmdbMovie = TmdbMovie(
            title = "Spider Man",
            releaseDate = LocalDate.now(),
            posterPath = "/poster.png",
            backdropPath = "/backdrop.png",
            voteCount = 510_000,
            popularity = 1_000_000.0
        )
        whenever(moviesService.details(any(), anyOrNull())).thenReturn(Response.success(tmdbMovie))

        val movie = repo.getMovieDetails("spider-man")

        assertEquals(tmdbMovie.title, movie.title)
    }

    @Test
    fun `Should get similar movies with success`() = runBlocking {
        whenever(genresService.movie()).thenReturn(
            Response.success(
                TmdbMovieGenreResults(
                    genres = listOf(
                        TmdbMovieGenre(
                            id = 1,
                            name = "Action"
                        ),
                        TmdbMovieGenre(
                            id = 2,
                            name = "Adventure"
                        ),
                        TmdbMovieGenre(
                            id = 3,
                            name = "Horror"
                        ),
                    )
                )
            )
        )
        whenever(moviesService.similar(any(), anyOrNull())).thenReturn(
            Response.success(
                TmdbSimilarMovieResultsPage(
                    page = 1,
                    totalPages = 1,
                    totalResults = 2,
                    results = listOf(
                        TmdbSimilarMovie(
                            title = "Superman vs Batman",
                            voteCount = 100_000,
                            genres = listOf(1, 2, 3),
                            releaseDate = LocalDate.now(),
                            posterPath = "",
                            backdropPath = ""
                        ),
                        TmdbSimilarMovie(
                            title = "The Amazing Superman",
                            voteCount = 200_000,
                            genres = listOf(1, 2, 3),
                            releaseDate = LocalDate.now().plusYears(10),
                            posterPath = "",
                            backdropPath = ""
                        )
                    )
                )
            )
        )
        val similarMovies = repo.getSimilarMovies("superman")
        assertTrue(similarMovies.size == 2)
    }

}