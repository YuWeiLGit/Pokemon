package com.example.testapplication.Repository

import com.example.testapplication.Model.Pokemon
import com.example.testapplication.internet.ApiService
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getPokemonData(): List<Pokemon> {
        return apiService.getPokemon()
    }


}