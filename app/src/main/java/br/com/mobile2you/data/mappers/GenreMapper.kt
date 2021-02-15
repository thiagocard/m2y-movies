package br.com.mobile2you.data.mappers

import br.com.mobile2you.data.model.TmdbMovieGenre
import br.com.mobile2you.domain.model.Genre

class GenreMapper : Mapper<TmdbMovieGenre, Genre> {

    override fun map(input: TmdbMovieGenre): Genre {
        return Genre(
            id = input.id,
            name = input.name
        )
    }

}
