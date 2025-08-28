package com.example.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemon.ui.screen.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val repository = PokemonRepository(context)
            val viewModel: PokemonViewModel = viewModel { PokemonViewModel(repository) }
            val navController = rememberNavController()
            PokemonAppTheme {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            viewModel = viewModel,
                            onNavigateToDetail = { id -> navController.navigate("detail/$id") },
                            onNavigateToFilter = { navController.navigate("filter") }
                        )
                    }
                    composable(
                        "detail/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id") ?: -1
                        PokemonDetailScreen(
                            viewModel = viewModel,
                            pokemonId = id,
                            navController = navController
                        )
                    }
                    composable("filter") {
                        FilterScreen(
                            onApply = { name, type ->
                                viewModel.searchPokemons(name, type)
                                navController.popBackStack()
                            },
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}