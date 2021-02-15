package br.com.mobile2you.di

import br.com.mobile2you.BuildConfig
import br.com.mobile2you.data.TmdbApi
import br.com.mobile2you.data.mappers.GenreMapper
import br.com.mobile2you.data.mappers.Mapper
import br.com.mobile2you.data.mappers.MovieMapper
import br.com.mobile2you.data.mappers.SimilarMovieMapper
import br.com.mobile2you.data.model.TmdbMovie
import br.com.mobile2you.data.model.TmdbMovieGenre
import br.com.mobile2you.data.model.TmdbSimilarMovie
import br.com.mobile2you.domain.model.Genre
import br.com.mobile2you.domain.model.Movie
import br.com.mobile2you.domain.model.SimilarMovie
import br.com.mobile2you.domain.repository.MovieRepository
import br.com.mobile2you.domain.repository.MovieRepositoryImpl
import br.com.mobile2you.ui.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val tmdbModule = module {
    factory { TmdbApi(apiKey = BuildConfig.TMDB_API_KEY) }
    factory { (get() as TmdbApi).moviesService }
    factory { (get() as TmdbApi).genresService }
}

val mappersModule = module {
    factory<Mapper<TmdbMovie, Movie>>(qualifier = named("movieMapper")) { MovieMapper() }
    factory<Mapper<TmdbSimilarMovie, SimilarMovie>>(qualifier = named("similarMovieMapper")) { SimilarMovieMapper() }
    factory<Mapper<TmdbMovieGenre, Genre>>(qualifier = named("genreMapper")) { GenreMapper() }
}

val appModule = module {
    factory<MovieRepository> {
        MovieRepositoryImpl(
            get(),
            get(),
            get(named("movieMapper")),
            get(named("similarMovieMapper"))
        )
    }
    viewModel { MovieViewModel(get()) }
}
