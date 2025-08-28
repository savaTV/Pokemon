// app/src/main/java/com/example/pokemon/HomeScreen.kt
package com.example.pokemon

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    repository: PokemonRepository,
    onNavigateToDetail: (Pokemon) -> Unit,
    onSearch: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var pokemons by remember { mutableStateOf<List<Pokemon>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var query by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var showSearch by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            when (val result = repository.loadPokemons()) {
                is NetworkResult.Success -> {
                    pokemons = result.data
                    isLoading = false
                }

                is NetworkResult.Error -> {
                    errorMessage = result.message
                    isLoading = false
                }
            }
        }
    }

    if (isLoading) {
        LoadingIndicator(modifier = Modifier.fillMaxSize())
    } else if (errorMessage != null) {
        Text(text = "Ошибка: $errorMessage", modifier = Modifier.padding(16.dp))
    } else {
        Column(modifier = modifier) {
            TopAppBar(
                title = {
                    if (showSearch) {
                        TextField(
                            value = query,
                            onValueChange = {
                                query = it
                                onSearch(it, type) // ✅ Вызов поиска
                            },
                            placeholder = { Text("Поиск покемонов...") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text("Список Покемонов")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { showSearch = !showSearch }) {
                        Icon(Icons.Default.Search, contentDescription = "Поиск")
                    }
                },
                actions = {
                    if (showSearch) {
                        IconButton(onClick = {
                            query = ""
                            type = ""
                            onSearch("", "") // ✅ Сброс фильтра
                        }) {
                            Icon(Icons.Default.Clear, contentDescription = "Очистить")
                        }
                    }
                }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(pokemons.filter { it.name.contains(query, ignoreCase = true) }) { pokemon ->
                    PokemonCard(
                        pokemon = pokemon,
                        onClick = { onNavigateToDetail(pokemon) }
                    )
                }
            }
        }
    }
}

