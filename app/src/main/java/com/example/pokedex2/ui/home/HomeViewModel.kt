package com.example.pokedex2.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonUiState>(PokemonUiState.Loading)
    val uiState: StateFlow<PokemonUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadUiState()
        }
    }

    private suspend fun loadUiState() {

        _uiState.value = PokemonUiState.Loading
        _uiState.value = try {
            val list = pokemonRepository.loadRemotePokemons()

            val pokemons = coroutineScope {
                list.results.map { item ->
                    async {
                        val id = item.url.trimEnd('/').substringAfterLast('/').toInt()
                        val full = pokemonRepository.loadFullPokemon(id)
                        Pokemon(
                            name = full.name,
                            imageUrl = full.sprites.front_default
                        )
                    }
                }.awaitAll()
            }

            PokemonUiState.Success(pokemons)
        } catch (e: IOException) {
            PokemonUiState.Error(e.message ?: "Erreur réseau")
        }

    }
}