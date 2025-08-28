// app/src/main/java/com/example/pokemon/PokemonRepository.kt
package com.example.pokemon

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class PokemonRepository(context: Context) {
    private val db = PokemonDatabase.getDatabase(context)
    private val dao = db.pokemonDao()
    private val remoteDataSource = PokeApiService
    val localDataSource = LocalDataSource(context) // <-- добавлено

    suspend fun loadPokemons(offset: Int = 0, limit: Int = 20): NetworkResult<List<Pokemon>> {
        return withContext(Dispatchers.IO) {
            try {
                val remoteData = remoteDataSource.fetchPokemons(offset, limit)
                dao.insertAll(remoteData)
                NetworkResult.Success(dao.getAllPokemons().first())
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun getAllPokemons(): Flow<List<Pokemon>> {
        return dao.getAllPokemons()
    }

    fun searchPokemons(query: String): Flow<List<Pokemon>> {
        return dao.searchPokemons(query)
    }

    fun searchPokemons(name: String, type: String): Flow<List<Pokemon>> {
        return dao.searchPokemons("$name $type")
    }
}