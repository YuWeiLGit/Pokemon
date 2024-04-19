package com.example.pokemonbook.repository

import android.util.Log
import com.example.pokemonbook.model.ApiResult
import com.example.pokemonbook.model.Pokemon
import com.example.pokemonbook.utils.PokemonSortHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {
    private var cacheData = mutableMapOf<String, Pokemon>()
    private var totalPokemonSize: Int? = null

    suspend fun changePokemonCollectStatus(id: String) {
        val pokemon = cacheData.getOrDefault(id, localRepository.getPokemonById(id))
        pokemon?.let {
            pokemon.isCollect = !pokemon.isCollect
            cacheData[id] = pokemon
            localRepository.updatePokemon(pokemon)
        }
    }

    fun getTotalPokemonSize(): Int? {
        return totalPokemonSize
    }

    suspend fun getPokemonById(id: String): Flow<Pokemon> = flow {
        val pokemon = cacheData.getOrDefault(id, localRepository.getPokemonById(id))
        pokemon?.let {
            emit(pokemon)
        }
    }.flowOn(Dispatchers.IO)

    fun getPokemonData(): Flow<ApiResult<List<Pokemon>>> = flow {
        val localData = localRepository.getAllPokemon()
        if (localData.isNotEmpty()) {
            cacheData = PokemonSortHelper.sortByPokemonId(localData)
            totalPokemonSize = localData.size
            Log.i("RECEIVE DATA", "FROM DB")
            val result = ApiResult.Success(localData)
            emit(result)
        } else {
            when (val remoteData = remoteRepository.getPokemonData()) {
                is ApiResult.Failed -> {
                    emit(remoteData)
                }

                is ApiResult.Success -> {
                    remoteData.data?.let {
                        totalPokemonSize = it.size
                        cacheData = PokemonSortHelper.sortByPokemonId(it)
                        coroutineScope {
                            launch(Dispatchers.IO) {
                                localRepository.insertAllPokemon(*it.toTypedArray())
                                Log.i("SAVE DATA", "SAVE TO DB")
                            }
                        }
                    }
                    emit(remoteData)
                }
            }
        }
    }.flowOn(Dispatchers.IO)


}