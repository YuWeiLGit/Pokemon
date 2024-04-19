package com.example.pokemonbook.utils

import androidx.annotation.ColorRes
import com.example.pokemonbook.R

object ColorHelper {

    private val colorMap = mapOf(
        "grass" to R.color.grass,
        "fire" to R.color.fire,
        "water" to R.color.water,
        "bug" to R.color.bug,
        "normal" to R.color.normal,
        "poison" to R.color.poison,
        "electric" to R.color.electric,
        "ground" to R.color.ground,
        "fairy" to R.color.fairy,
        "fighting" to R.color.fighting,
        "psychic" to R.color.psychic,
        "rock" to R.color.rock,
        "ghost" to R.color.ghost,
        "ice" to R.color.ice,
        "dragon" to R.color.dragon,
        "dark" to R.color.dark,
        "steel" to R.color.steel,
        "flying" to R.color.flying
    )

    @ColorRes
    fun getColor(text: String): Int {
        return colorMap.getOrDefault(text.lowercase(), R.color.normal)
    }

}