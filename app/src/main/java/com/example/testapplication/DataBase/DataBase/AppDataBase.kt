package com.example.testapplication.DataBase.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testapplication.DataBase.Dao.PokemonDao
import com.example.testapplication.DataBase.TypeConverter.PokemonTypeConverter
import com.example.testapplication.Model.Pokemon

@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
@TypeConverters(PokemonTypeConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val _instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_dataBase"
                ).build()
                INSTANCE = _instance
                _instance
            }

        }

    }

}