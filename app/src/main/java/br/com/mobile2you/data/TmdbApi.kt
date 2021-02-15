package br.com.mobile2you.data

import br.com.mobile2you.data.service.GenresService
import br.com.mobile2you.data.service.MoviesService
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.LocalDate
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class TmdbApi(apiUrl: String = "https://api.themoviedb.org/3/", apiKey: String) {

    private val retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .registerTypeAdapter(
                        LocalDate::class.java,
                        object : JsonDeserializer<LocalDate?> {
                            override fun deserialize(
                                json: JsonElement?,
                                typeOfT: Type?,
                                context: JsonDeserializationContext?
                            ): LocalDate? {
                                return json?.asString?.let { LocalDate.parse(it) }
                            }
                        })
                    .create()
            )
        ).client(
            OkHttpClient.Builder()
                .addInterceptor(TmdbApiInterceptor(apiKey))
                .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        ).build()

    val moviesService: MoviesService = retrofit.create(MoviesService::class.java)

    val genresService: GenresService = retrofit.create(GenresService::class.java)

}

class TmdbApiInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val url = request.url.newBuilder().setEncodedQueryParameter(PARAM_API_KEY, apiKey).build()
        return chain.proceed(request.newBuilder().url(url).build())
    }

    companion object {
        private const val PARAM_API_KEY = "api_key"
    }

}

