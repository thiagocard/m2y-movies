package br.com.mobile2you.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mobile2you.databinding.ItemMovieBinding
import br.com.mobile2you.domain.model.SimilarMovie
import coil.load

class MovieAdapter(
    private val data: List<SimilarMovie>
) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

}

class MovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(similarMovie: SimilarMovie) {
        binding.poster.load(similarMovie.poster)
        binding.title.text = similarMovie.title
        binding.yearGenres.text = similarMovie.yearAndGenres
    }

}