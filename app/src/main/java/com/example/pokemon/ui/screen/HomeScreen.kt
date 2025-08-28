package com.example.pokemon.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemon.LoadingIndicator
import com.example.pokemon.PokemonCard
import com.example.pokemon.PokemonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: PokemonViewModel,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToFilter: () -> Unit,
    modifier: Modifier = Modifier
) {
    // ✅ Объявляем состояние для фильтра
    var showFilterDialog by remember { mutableStateOf(false) }

    val pokemonList by viewModel.pokemonList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val gridState = rememberLazyGridState()

    val isAtBottom by remember {
        derivedStateOf {
            val layoutInfo = gridState.layoutInfo
            layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
        }
    }


    LaunchedEffect(isAtBottom) {
        if (isAtBottom && !isLoading) {
            viewModel.loadPokemons()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Pokemon")
                    }
                },
                navigationIcon = {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Logo", modifier = Modifier.size(40.dp))
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (isLoading && pokemonList.isEmpty()) {
                LoadingIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (errorMessage != null) {
                Text(
                    text = "Ошибка: $errorMessage",
                    modifier = Modifier.padding(16.dp)
                )
            } else if (pokemonList.isEmpty()) {
                Text(
                    text = "Нет покемонов по запросу",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = gridState,
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(pokemonList) { pokemon ->
                        PokemonCard(
                            pokemon = pokemon,
                            onClick = { onNavigateToDetail(pokemon.id) }
                        )
                    }
                }
            }
        }
    }

    // ✅ Здесь можно добавить BottomSheet или Dialog, если хотите
}