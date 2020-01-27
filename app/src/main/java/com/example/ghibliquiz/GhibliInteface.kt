package com.example.ghibliquiz

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GhibliInteface {
    @GET("people")
    fun listAllCharacters(): Call<List<Character>>
    @GET("films")
    fun listAllMovies(): Call<List<Movie>>
}