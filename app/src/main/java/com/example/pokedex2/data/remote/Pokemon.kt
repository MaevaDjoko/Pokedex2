package com.example.pokedex2.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val weight: Int,
)

@Serializable
data class Sprites(
    val front_default: String?,
)