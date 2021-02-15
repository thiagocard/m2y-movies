package br.com.mobile2you.data.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate

data class TmdbMovie(
    val title: String,
    @SerializedName("release_date")
    val releaseDate: LocalDate,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("vote_count")
    val voteCount: Int,
    val popularity: Double,
)
