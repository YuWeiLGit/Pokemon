package com.example.testapplication.Utils

import com.example.testapplication.Model.Pokemon

object PokemonSortHelper {
    fun sortByPokemonType(pokemons: List<Pokemon>): MutableMap<String, List<Pokemon>> {
        return pokemons.flatMap { pokemon ->
            pokemon.typeOfPokemon.orEmpty().map { type ->
                type to pokemon
            }
        }.groupBy({ it.first }, { it.second })
            .toMutableMap()
    }

    fun sortByPokemonId(pokemons: List<Pokemon>): MutableMap<String, Pokemon> {
        val map = mutableMapOf<String, Pokemon>()
        for (pokemon in pokemons) {
            map[pokemon.id] = pokemon
        }
        return map
    }
}