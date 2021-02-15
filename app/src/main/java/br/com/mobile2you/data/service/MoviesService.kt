package br.com.mobile2you.data.service

import br.com.mobile2you.data.model.TmdbMovie
import br.com.mobile2you.data.model.TmdbSimilarMovieResultsPage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/{id}")
    suspend fun details(
        @Path("id") id: String,
        @Query("language") language: String? = "en"
    ): Response<TmdbMovie>

    @GET("movie/{id}/similar")
    suspend fun similar(
        @Path("id") id: String,
        @Query("language") language: String? = "en"
    ): Response<TmdbSimilarMovieResultsPage>

}