package br.com.mobile2you.test

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import java.io.File
import java.net.HttpURLConnection

open class MockWebServerTest {

    protected val server = MockWebServer()

    @Before
    fun setUp() = server.start(port = 8082)

    @After
    fun tearDown() = server.shutdown()

    protected fun createResponse(path: String? = null, sc: Int = HttpURLConnection.HTTP_OK) =
        MockResponse().apply {
            if (path != null) {
                setBody(File(this@MockWebServerTest.javaClass.classLoader?.getResource(path)!!.file).readText())
            }
            setResponseCode(sc)
        }

}
