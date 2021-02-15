package br.com.mobile2you.data.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate

data class TmdbSimilarMovieResultsPage(
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    val results: List<TmdbSimilarMovie>
)

data class TmdbSimilarMovie(
    val title: String,
    @SerializedName("release_date")
    val releaseDate: LocalDate,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("genre_ids")
    val genres: List<Int>,
)