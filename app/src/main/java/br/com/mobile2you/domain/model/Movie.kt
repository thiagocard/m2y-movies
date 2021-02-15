package br.com.mobile2you.domain.model

data class Movie(
    val title: String,
    val poster: String?,
    val backdrop: String?,
    val likeCount: String,
    val views: String,
    val isFavorite: Boolean
)