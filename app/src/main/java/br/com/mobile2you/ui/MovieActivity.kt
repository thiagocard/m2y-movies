package br.com.mobile2you.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import br.com.mobile2you.R
import br.com.mobile2you.databinding.ActivityMovieBinding
import br.com.mobile2you.domain.model.Movie
import coil.load
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val THE_DARK_KNIGHT = "155"

class MovieActivity : AppCompatActivity() {

    private val viewModel by viewModel<MovieViewModel>()

    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favorite.setOnClickListener {
            viewModel.toggleFavorite()
        }

        viewModel.setMovieId(THE_DARK_KNIGHT)

        viewModel.movie.observe(this) { movieResult ->
            binding.progressBar.isVisible = false
            if (movieResult.isSuccess) {
                val movie = movieResult.getOrThrow()
                bindMovie(movie)
            } else {
                bindError(getString(R.string.msg_error_movie))
            }
        }
        viewModel.similarMovies.observe(this) { similarMoviesResult ->
            if (similarMoviesResult.isSuccess) {
                binding.recyclerView.adapter = MovieAdapter(similarMoviesResult.getOrThrow())
            } else {
                Snackbar.make(
                    binding.root, R.string.msg_error_similar_movies, Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun bindError(msg: String) {
        binding.error.isVisible = true
        binding.error.text = msg
    }

    private fun bindMovie(movie: Movie) {
        binding.mainView.isVisible = true
        binding.title.text = movie.title
        binding.likes.text = movie.likeCount
        binding.views.text = movie.views
        binding.favorite.setImageDrawable(
            if (movie.isFavorite) {
                ContextCompat.getDrawable(
                    this@MovieActivity,
                    R.drawable.ic_baseline_favorite_24
                )
            } else {
                ContextCompat.getDrawable(
                    this@MovieActivity,
                    R.drawable.ic_baseline_favorite_border_24
                )
            }
        )
        binding.backdrop.load(movie.backdrop)
    }

}