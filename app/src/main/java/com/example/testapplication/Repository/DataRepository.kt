package com.example.testapplication.Repository

import android.util.Log
import com.example.testapplication.Model.Pokemon
import com.example.testapplication.Utils.PokemonSortHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Logger
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {
    var cacheData = mutableMapOf<String, Pokemon>()

    fun queryIsPokemonCollect(id: String): Flow<Pair<String, Boolean>> =
        flow {
            if (cacheData[id] != null) {
                emit(Pair(id, cacheData[id]!!.isCollect))
                return@flow
            }
            val pokemon = if (localRepository.getPokemonById(id) != null) {
                localRepository.getPokemonById(id)
            } else {
                null
            }
            pokemon?.let {
                cacheData[id] = pokemon
                emit(Pair(id, pokemon.isCollect))
            }

        }.flowOn(Dispatchers.IO)

    suspend fun changePokemonCollectStatus(id: String) {
        val pokemon = cacheData.getOrDefault(id, localRepository.getPokemonById(id))
        pokemon?.let {
            pokemon.isCollect = !pokemon.isCollect
            cacheData[id] = pokemon
            localRepository.updatePokemon(pokemon)
        }
    }

    fun getPokemonData(): Flow<MutableMap<String, List<Pokemon>>> = flow {
        val localData = localRepository.getAllPokemon()
        if (localData.isNotEmpty()) {
            val map = PokemonSortHelper.sortByPokemonType(localData)
            cacheData = PokemonSortHelper.sortByPokemonId(localData)
            Log.i("RECEIVE DATA", "FROM DB")
            emit(map)
        } else {
            try {
                val remoteData = remoteRepository.getPokemonData()
                cacheData = PokemonSortHelper.sortByPokemonId(remoteData)
                coroutineScope {
                    launch(Dispatchers.IO) {
                        localRepository.insertAllPokemon(*remoteData.toTypedArray())
                        Log.i("SAVE DATA", "SAVE TO DB")
                    }
                }
                val map = PokemonSortHelper.sortByPokemonType(remoteData)
                Log.i("RECEIVE DATA", "FROM API")
                emit(map)
            } catch (e: Exception) {
                Log.d("API ERROR", "API SERVER ERROR")
            }
        }

    }.flowOn(Dispatchers.IO)


}