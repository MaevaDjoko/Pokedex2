package com.example.pokedex2.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpecies(
    val id: Int,
    val names: List<Name>
)

@Serializable
data class Name(
    val language: Language,
    val name: String
)

@Serializable
data class Language(val name: String, val url: String)