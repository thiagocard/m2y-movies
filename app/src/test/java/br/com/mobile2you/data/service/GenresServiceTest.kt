package br.com.mobile2you.data.service

import br.com.mobile2you.data.TmdbApi
import br.com.mobile2you.test.MockWebServerTest
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test

class GenresServiceTest : MockWebServerTest() {

    private val genresService = TmdbApi(apiUrl = "http://localhost:8082/", apiKey = "?").genresService

    @Test
    fun `Should get movie genres with success`() = runBlocking {
        server.enqueue(createResponse("get_movie_genres.json"))
        val genres = genresService.movie()
        assertNotNull(genres.body())
    }


}