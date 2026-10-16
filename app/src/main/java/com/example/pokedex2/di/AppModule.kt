package com.example.pokedex2.di

import com.example.pokedex2.data.remote.PokemonsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providePokemonRemoteDataSource(): PokemonsRemoteDataSource {
        return PokemonsRemoteDataSource()
    }

}