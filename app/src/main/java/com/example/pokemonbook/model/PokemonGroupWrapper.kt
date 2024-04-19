package com.example.pokemonbook.model

data class PokemonGroupWrapper(
    val type: String,
    var pokemonList: MutableList<Pokemon>,
    var isOpen: Boolean = false
)
