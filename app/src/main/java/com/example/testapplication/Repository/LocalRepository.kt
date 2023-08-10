package com.example.testapplication.Repository

import com.example.testapplication.DataBase.Dao.PokemonDao
import com.example.testapplication.Model.Pokemon
import javax.inject.Inject

class LocalRepository @Inject constructor(private val pokemonDao: PokemonDao) {

    suspend fun insertAllPokemon(vararg pokemons: Pokemon) {
        pokemonDao.insertAll(*pokemons)
    }

    suspend fun getAllPokemon(): List<Pokemon> {
        return pokemonDao.getAll()
    }

    suspend fun getPokemonById(id: String): Pokemon? {
        return pokemonDao.getPokemonById(id)
    }

    suspend fun updatePokemon(pokemon: Pokemon) {
        pokemonDao.updatePokemon(pokemon)
    }


}