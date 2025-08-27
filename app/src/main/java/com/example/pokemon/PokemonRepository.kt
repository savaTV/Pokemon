// app/src/main/java/com/example/pokemon/PokemonRepository.kt
package com.example.pokemon

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PokemonRepository(context: Context) {
    val localDataSource = LocalDataSource(context)
    private val remoteDataSource = PokeApiService

    suspend fun loadPokemons(offset: Int = 0, limit: Int = 20): NetworkResult<List<Pokemon>> {
        return withContext(Dispatchers.IO) {
            try {
                val remoteData = remoteDataSource.fetchPokemons(offset, limit)
                localDataSource.savePokemons(remoteData)
                NetworkResult.Success(localDataSource.getAllPokemons())
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun searchPokemons(query: String, type: String = ""): Flow<List<Pokemon>> {
        return localDataSource.searchPokemons(query, type = type)
    }

    suspend fun loadMorePokemons(page: Int): List<Pokemon> {
        val response = api.getPokemonList(page * pageSize, pageSize)
        return response.results
    }
}