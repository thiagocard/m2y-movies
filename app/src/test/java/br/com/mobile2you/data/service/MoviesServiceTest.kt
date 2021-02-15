package br.com.mobile2you.data.service

import br.com.mobile2you.data.TmdbApi
import br.com.mobile2you.test.MockWebServerTest
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test

class MoviesServiceTest : MockWebServerTest() {

    private val moviesService = TmdbApi(apiUrl = "http://localhost:8082/", apiKey = "?").moviesService


    @Test
    fun `Should get movie details with success`() = runBlocking {
        server.enqueue(createResponse("get_movie_details.json"))
        val movie = moviesService.details("155")
        assertNotNull(movie.body())
    }

    @Test
    fun `Should get similar movies with success`() = runBlocking {
        server.enqueue(createResponse("get_similar_movies.json"))
        val similars = moviesService.similar("155")
        assertNotNull(similars.body())
    }

}