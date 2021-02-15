package br.com.mobile2you.data.service

import br.com.mobile2you.data.model.TmdbMovieGenreResults
import retrofit2.Response
import retrofit2.http.GET

interface GenresService {

    @GET("genre/movie/list")
    suspend fun movie(): Response<TmdbMovieGenreResults>

}