package br.com.mobile2you.data.mappers

import br.com.mobile2you.data.model.TmdbMovie
import br.com.mobile2you.domain.model.Movie
import br.com.mobile2you.extension.formatAsMultipleOfHundread

class MovieMapper : Mapper<TmdbMovie, Movie> {

    override fun map(input: TmdbMovie): Movie {
        return Movie(
            title = input.title,
            poster = "https://image.tmdb.org/t/p/w500/${input.posterPath}",
            backdrop = "https://image.tmdb.org/t/p/w1280/${input.backdropPath}",
            likeCount = "${input.voteCount.formatAsMultipleOfHundread()} likes",
            views = "${input.popularity} views",
            isFavorite = false
        )
    }

}
