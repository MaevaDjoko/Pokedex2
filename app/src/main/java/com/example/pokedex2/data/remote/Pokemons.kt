package com.example.pokedex2.data.remote

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class Pokemons(
    val count: Int,
    val next: String,
    val results: List<Data>
)

@Serializable
data class Data (val name: String, val url: String)
