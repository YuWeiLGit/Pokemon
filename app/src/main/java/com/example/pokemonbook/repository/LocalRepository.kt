package com.example.pokemonbook.repository

import com.example.pokemonbook.dataBase.dao.PokemonDao
import com.example.pokemonbook.model.Pokemon
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