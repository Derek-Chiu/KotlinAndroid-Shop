package com.derek.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.derek.shop.Model.Movie
import com.derek.shop.api.MovieService
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.row_movie.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieActivity : AppCompatActivity(), AnkoLogger {

  var movies: List<Movie>? = null
  val retrofit = Retrofit.Builder()
    .baseUrl("https://api.myjson.com/bins/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_movie)

    doAsync {

      // Gson way
//      val json = URL("https://api.myjson.com/bins/p25iw").readText()
//      movies = Gson().fromJson<List<Movie>>(json, object: TypeToken<List<Movie>>(){}.type)

      // Retrofit way
      val movieService = retrofit.create(MovieService::class.java)
      movies = movieService.listMovies()
        .execute()
        .body()
//      movies?.forEach {
//        info("${it.Title} ${it.imdbRating} ${it.Poster}")
//      }
      uiThread {
        recyclerView.layoutManager = LinearLayoutManager(this@MovieActivity)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = MovieAdapter()
      }
    }
  }

  inner class MovieAdapter: RecyclerView.Adapter<MovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.row_movie, parent, false)
      return MovieHolder(view)
    }

    override fun getItemCount(): Int {
      val size = movies?.size?: 0
      return size
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
      val movie = movies?.get(position)
      holder.bindMovie(movie!!)
    }


  }

  inner class MovieHolder(view: View): RecyclerView.ViewHolder(view) {
    val titleText: TextView = view.movie_title
    val imdbText: TextView = view.imdb
    val directorText: TextView = view.director
    val poster: ImageView = view.poster

    fun bindMovie(movie: Movie) {
      titleText.text = movie.Title
      imdbText.text = movie.imdbRating
      directorText.text = movie.Director
      Glide.with(this@MovieActivity)
        .load(movie.Poster)
        .override(300)
        .into(poster)
    }
  }
}
