package com.example.testapplication.DI

import android.app.Application
import android.content.Context
import com.example.testapplication.DataBase.Dao.PokemonDao
import com.example.testapplication.DataBase.DataBase.AppDataBase
import com.example.testapplication.Repository.DataRepository
import com.example.testapplication.Repository.LocalRepository
import com.example.testapplication.internet.ApiService
import com.example.testapplication.Repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(apiService: ApiService): RemoteRepository {
        return RemoteRepository(apiService)
    }

    @Provides
    @Singleton
    fun providePokemonDao(appDataBase: AppDataBase): PokemonDao {
        return appDataBase.pokemonDao()
    }

    @Provides
    @Singleton
    fun provideLocalRepository(pokemonDao: PokemonDao): LocalRepository {
        return LocalRepository(pokemonDao)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideAppDataBase(context: Context): AppDataBase {
        return AppDataBase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideDataRepository(
        localRepository: LocalRepository,
        remoteRepository: RemoteRepository
    ): DataRepository {
        return DataRepository(localRepository, remoteRepository)
    }


}