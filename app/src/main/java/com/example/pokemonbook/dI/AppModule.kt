package com.example.pokemonbook.dI

import android.app.Application
import android.content.Context
import com.example.pokemonbook.dataBase.dao.PokemonDao
import com.example.pokemonbook.dataBase.dataBase.AppDataBase
import com.example.pokemonbook.internet.ApiService
import com.example.pokemonbook.data.repository.DataRepository
import com.example.pokemonbook.data.repository.LocalRepository
import com.example.pokemonbook.data.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
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