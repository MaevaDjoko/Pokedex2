package com.example.pokedex2.ui.home

data class Pokemon(val name: String, val imageUrl: String?)

sealed interface PokemonUiState {
    data object Loading : PokemonUiState
    data class Success(val pokemons: List<Pokemon>) : PokemonUiState
    data class Error(val message: String) : PokemonUiState
}