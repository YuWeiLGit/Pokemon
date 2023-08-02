package com.example.testapplication.internet

import android.util.Log
import com.example.testapplication.Model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.Exception

class DataRepository @Inject constructor(private val apiService: ApiService) {


    fun getPokemonData(): Flow<MutableMap<String, List<Pokemon>>> = flow {
        try {
            val response = apiService.getPokemon()
            val map = response.flatMap { pokemon ->
                pokemon.typeOfPokemon.orEmpty().map { type ->
                    type to pokemon
                }
            }.groupBy({ it.first }, { it.second })
                .toMutableMap()
            emit(map)
        } catch (e: Exception) {
            Log.d("API ERROR", "API SERVICE ERROR")
        }
    }.flowOn(Dispatchers.IO)


}