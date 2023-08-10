package com.example.testapplication.Repository

import android.util.Log
import com.example.testapplication.Model.Pokemon
import com.example.testapplication.Utils.PokemonSortHelper
import com.example.testapplication.internet.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.Exception

class RemoteRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getPokemonData(): List<Pokemon> {
        return apiService.getPokemon()
    }


}