package br.com.mobile2you.domain.repository

import br.com.mobile2you.domain.model.Movie
import br.com.mobile2you.domain.model.SimilarMovie

interface MovieRepository {

    suspend fun getMovieDetails(id: String): Movie

    suspend fun getSimilarMovies(id: String): List<SimilarMovie>

}