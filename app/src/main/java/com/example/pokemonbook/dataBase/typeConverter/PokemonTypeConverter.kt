package com.example.pokemonbook.dataBase.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToList(value: String?): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun listToString(list: List<String>?): String {
        return gson.toJson(list)
    }


}