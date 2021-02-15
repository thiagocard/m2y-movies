package br.com.mobile2you.data.model

data class TmdbMovieGenreResults(
    val genres: List<TmdbMovieGenre>
)

data class TmdbMovieGenre(
    val id: Int,
    val name: String
)