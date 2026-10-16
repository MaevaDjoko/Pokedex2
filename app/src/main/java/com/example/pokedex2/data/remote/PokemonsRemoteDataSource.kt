package com.example.pokedex2.data.remote

import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class PokemonsRemoteDataSource {
    suspend fun loadPokemons(): Pokemons {
        val response: Pokemons =
            PokeApiService.client.get("https://pokeapi.co/api/v2/pokemon").body()
        return response
    }

    suspend fun loadPokemon(id: Int): Pokemon {
        val response: Pokemon =
            PokeApiService.client.get("https://pokeapi.co/api/v2/pokemon/$id").body()
        return response
    }

    suspend fun loadPokemonSpecies(id: Int): PokemonSpecies {
        val response: PokemonSpecies =
            PokeApiService.client.get("https://pokeapi.co/api/v2/pokemon-species/$id").body()
        return response
    }

    suspend fun loadFullPokemon(id: Int): Pokemon = coroutineScope {
        val pokemonsAsync = async { loadPokemon(id) }
        val speciesAsync = async { loadPokemonSpecies(id) }

        val pokemon = pokemonsAsync.await()
        val species = speciesAsync.await()

        val nameFr = species.names.first { it.language.name == "fr" }.name
        val imageUrl = pokemon.sprites
        val weight = pokemon.weight

        return@coroutineScope Pokemon(id, nameFr, imageUrl, weight)
    }
}