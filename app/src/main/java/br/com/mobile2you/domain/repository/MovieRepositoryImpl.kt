package br.com.mobile2you.domain.repository

import br.com.mobile2you.data.mappers.Mapper
import br.com.mobile2you.data.model.TmdbMovie
import br.com.mobile2you.data.model.TmdbMovieGenre
import br.com.mobile2you.data.model.TmdbSimilarMovie
import br.com.mobile2you.data.service.GenresService
import br.com.mobile2you.data.service.MoviesService
import br.com.mobile2you.domain.model.Movie
import br.com.mobile2you.domain.model.SimilarMovie
import br.com.mobile2you.extension.bodyOrThrow

class MovieRepositoryImpl(
    private val moviesService: MoviesService,
    private val genresService: GenresService,
    private val movieMapper: Mapper<TmdbMovie, Movie>,
    private val similarMovieMapper: Mapper<TmdbSimilarMovie, SimilarMovie>,
) : MovieRepository {

    override suspend fun getMovieDetails(id: String): Movie {
        val movie = moviesService.details(id).bodyOrThrow()
        return movieMapper.map(movie)
    }

    override suspend fun getSimilarMovies(id: String): List<SimilarMovie> {
        val movies = moviesService.similar(id).bodyOrThrow()
        val movieGenres = genresService.movie().body()
        return movies.results.map { movie ->
            similarMovieMapper.map(movie).copy(yearAndGenres = movie.genres.joinToString { findGenreNameById(it, movieGenres?.genres) })
        }
//        return movies.results.map { sm ->
//            val genres =
//                sm.genres.map { genreId -> movieGenres?.genres?.find { movieGenre -> genreId == movieGenre.id }?.name }
//            similarMovieMapper.map(sm)
//                .copy(yearAndGenres = "${sm.releaseDate.year}  ${genres.joinToString(separator = ",")}")
//        }
    }

    private fun findGenreNameById(id: Int, genres: List<TmdbMovieGenre>?): String {
        return genres?.find { it.id == id }?.name ?: ""
    }

}