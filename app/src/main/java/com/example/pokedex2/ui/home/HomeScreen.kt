package com.example.pokedex2.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.pokedex2.R
import com.example.pokedex2.ui.theme.Pokedex2Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        stringResource(R.string.title_home),
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            painter = painterResource(R.drawable.search_24px),
                            contentDescription = "Add to favorites",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        PokeCardCollection(innerPadding)
    }
}

@Composable
fun PokeCard(
    name: String,
    imageUrl: String?,
    onDetailsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface (modifier = modifier) {
        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 9.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                AsyncImage(
                    model = imageUrl,
                    contentDescription = name,
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = { onDetailsClick() },
                    colors = ButtonDefaults.elevatedButtonColors(),
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.details),
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}

@Composable
fun PokeCardCollection(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is PokemonUiState.Loading -> Box(
            modifier = modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }

        is PokemonUiState.Error -> Box(
            modifier = modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center
        ) { Text(state.message) }

        is PokemonUiState.Success -> PokeCardList(
            pokemons = state.pokemons,
            innerPadding = innerPadding,
            modifier = modifier
        )
    }
}

@Composable
fun PokeCardList(
    pokemons: List<Pokemon>,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = innerPadding,
        modifier = modifier.consumeWindowInsets(WindowInsets.systemBars)
    ) {
        items(pokemons) { pokemon ->
            PokeCard(
                name = pokemon.name,
                imageUrl = pokemon.imageUrl,
                onDetailsClick = {}
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomePreview() {
    Pokedex2Theme {
        Home()
    }
}

@Preview
@Composable
private fun PokeCardPreview() {
    Pokedex2Theme {
        PokeCard(
            name = "bulbasaur",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
            onDetailsClick = {}
        )
    }
}

@Preview
@Composable
private fun PokeCardCollectionPreview() {
    Pokedex2Theme {
        PokeCardList(
            pokemons = listOf(
                Pokemon("1", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
                Pokemon("2", "url2")
            ),
            innerPadding = Modifier.padding(15.dp) as PaddingValues
        )
    }
}