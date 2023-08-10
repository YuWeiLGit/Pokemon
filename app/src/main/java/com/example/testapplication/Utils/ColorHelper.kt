package com.example.testapplication.Utils

object ColorHelper {

    val colorMap = mapOf(
        "grass" to "#2CDAB1",
        "fire" to "#F7706B",
        "water" to "#58ABF6",
        "bug" to "#2CDA90",
        "normal" to "#7986CB",
        "poison" to "#9F5BBA",
        "electric" to "#FFE252",
        "ground" to "#CA8179",
        "fairy" to "#FF86AF",
        "fighting" to "#F78C6B",
        "psychic" to "#FFCE4B",
        "rock" to "#955A54",
        "ghost" to "#7C5BBA",
        "ice" to "#58F1F6",
        "dragon" to "#7A9A64",
        "dark" to "#303943",
        "steel" to "#4A425A",
        "flying" to "#C379CB"
    )

    fun getColor(text: String): String {
        return colorMap.getOrDefault(text.lowercase(), "#7986CB")
    }

}