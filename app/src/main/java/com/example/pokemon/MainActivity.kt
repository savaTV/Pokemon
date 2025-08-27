// app/src/main/java/com/example/pokemon/MainActivity.kt
package com.example.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val repository = remember { PokemonRepository(context) }
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(
                        repository = repository,
                        onNavigateToDetail = { pokemon ->
                            navController.navigate("detail/${pokemon.id}")
                        },
                        onSearch = { name, type ->
                            repository.searchPokemons(name, type) // ✅ Выполняем поиск
                        }
                    )
                }
                composable("detail/{id}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: -1
                    val pokemons by repository.localDataSource.searchPokemons("id=$id").collectAsState(emptyList())
                    val pokemon = pokemons.firstOrNull()

                    if (pokemon != null) {
                        PokemonDetailScreen(pokemon = pokemon, navController = navController)
                    } else {
                        Text(text = "Покемон не найден")
                    }
                }
                composable("filter") {
                    FilterScreen(
                        onSearch = { query, type ->
                            navController.navigate("home") // ✅ Возвращаемся на главный экран
                        },
                        navController = navController // ✅ Передаём navController
                    )
                }
            }
        }
    }
}