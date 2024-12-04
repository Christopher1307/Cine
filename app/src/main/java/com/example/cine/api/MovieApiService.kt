package com.example.cine.api

import retrofit2.Call
import retrofit2.http.GET

interface MovieApiService {
    @GET("movie/popular")
    fun getPopularMovies(): Call<MovieResponse>
}