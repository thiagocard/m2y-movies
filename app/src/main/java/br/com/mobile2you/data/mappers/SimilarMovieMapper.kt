package br.com.mobile2you.data.mappers

import br.com.mobile2you.data.model.TmdbSimilarMovie
import br.com.mobile2you.domain.model.SimilarMovie

class SimilarMovieMapper : Mapper<TmdbSimilarMovie, SimilarMovie> {

    override fun map(input: TmdbSimilarMovie): SimilarMovie {
        return SimilarMovie(
            title = input.title,
            yearAndGenres = null, // Should be mapper after fetching all genres
            poster = "https://image.tmdb.org/t/p/w500/${input.posterPath}"
        )
    }

}
