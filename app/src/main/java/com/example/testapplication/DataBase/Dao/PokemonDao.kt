package com.example.testapplication.DataBase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testapplication.Model.Pokemon

@Dao
interface PokemonDao {

    @Query("SELECT * From POKEMON_TABLE ORDER BY id ASC")
    suspend fun getAll(): List<Pokemon>

    @Update
    suspend fun updatePokemon(pokemon: Pokemon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg pokemons: Pokemon)

    @Query("SELECT * From POKEMON_TABLE WHERE id = :id")
    suspend fun getPokemonById(id: String): Pokemon?

    @Query("SELECT COALESCE(isCollect, 0) From POKEMON_TABLE WHERE id = :id")
    suspend fun getIdCollectById(id: String): Boolean?
}