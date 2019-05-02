package com.derek.shop.api

import com.derek.shop.Model.Movie
import retrofit2.Call
import retrofit2.http.GET

interface MovieService {
  @GET("p25iw")
  fun listMovies(): Call<List<Movie>>
}