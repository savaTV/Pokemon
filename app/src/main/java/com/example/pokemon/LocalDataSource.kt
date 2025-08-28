// app/src/main/java/com/example/pokemon/LocalDataSource.kt
package com.example.pokemon

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class LocalDataSource(private val context: Context) {
    private val database: PokemonDatabase = PokemonDatabase.getDatabase(context)
    private val dao: PokemonDao = database.pokemonDao()

    suspend fun savePokemons(pokemons: List<Pokemon>) {
        dao.insertAll(pokemons)
    }

    suspend fun getAllPokemons(): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            dao.getAllPokemons().firstOrNull() ?: emptyList()
        }
    }

    fun searchPokemons(query: String): Flow<List<Pokemon>> {
        return dao.searchPokemons(query)
    }
}