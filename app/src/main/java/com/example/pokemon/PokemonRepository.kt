package com.example.pokemon

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class PokemonRepository(context: Context) {
    private val db = PokemonDatabase.getDatabase(context)
    private val dao = db.pokemonDao()
    private val remoteDataSource = PokeApiService

    suspend fun loadPokemons(offset: Int = 0, limit: Int = 20): NetworkResult<List<Pokemon>> {
        return withContext(Dispatchers.IO) {
            try {
                val remoteData = remoteDataSource.fetchPokemons(offset, limit)
                dao.insertAll(remoteData)
                NetworkResult.Success(dao.getAllPokemons().firstOrNull() ?: emptyList())
            } catch (e: Exception) {
                // Offline: return local
                val local = dao.getAllPokemons().firstOrNull() ?: emptyList()
                if (local.isNotEmpty()) {
                    NetworkResult.Success(local)
                } else {
                    NetworkResult.Error(e.message ?: "Ошибка загрузки, нет локальных данных")
                }
            }
        }
    }
    suspend fun getTotalPokemonCount(): Int {
        return withContext(Dispatchers.IO) {
            val response = PokeApiService.service.fetchPokemonCount() // 👈 исправлено
            response.count
        }
    }

    fun getAllPokemons(): Flow<List<Pokemon>> {
        return dao.getAllPokemons()
    }

    fun searchPokemons(name: String, type: String): Flow<List<Pokemon>> {
        return dao.searchByNameAndType("%$name%", "%$type%")
    }

    fun getPokemonById(id: Int): Flow<Pokemon?> {
        return dao.getPokemonById(id)
    }
}