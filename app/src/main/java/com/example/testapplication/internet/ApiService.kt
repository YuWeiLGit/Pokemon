package com.example.testapplication.internet

import com.example.testapplication.Model.Pokemon
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("mrcsxsiq/b94dbe9ab67147b642baa9109ce16e44/raw/97811a5df2df7304b5bc4fbb9ee018a0339b8a38")
    suspend fun getPokemon(): MutableList<Pokemon>
}