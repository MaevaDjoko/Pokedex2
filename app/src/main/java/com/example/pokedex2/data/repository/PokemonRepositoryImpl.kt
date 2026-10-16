package com.example.pokedex2.data.repository

import com.example.pokedex2.data.remote.Pokemon
import com.example.pokedex2.data.remote.Pokemons
import com.example.pokedex2.data.remote.PokemonsRemoteDataSource
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonsRemoteDataSource,
) : PokemonRepository {
    override suspend fun loadRemotePokemons(): Pokemons {
        val pokemons = remoteDataSource.loadPokemons()
        return pokemons
    }

    override suspend fun loadFullPokemon(id: Int): Pokemon {
        val pokemon = remoteDataSource.loadFullPokemon(id)
        return pokemon
    }
}