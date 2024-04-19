package com.example.pokemonbook.repository

import com.example.pokemonbook.model.Pokemon
import com.example.pokemonbook.internet.ApiService
import com.example.pokemonbook.model.ApiResult
import retrofit2.HttpException
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getPokemonData(): ApiResult<List<Pokemon>> {
        return try {
            ApiResult.Success(apiService.getPokemon())
        } catch (e: HttpException) {
            ApiResult.Failed(null, e.code().toString())
        }
    }
}