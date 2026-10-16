package com.example.pokedex2.data.repository

import com.example.pokedex2.data.remote.Pokemon
import com.example.pokedex2.data.remote.Pokemons

interface PokemonRepository {
    suspend fun loadRemotePokemons(): Pokemons
    suspend fun loadFullPokemon(id: Int): Pokemon
}