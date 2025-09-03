package com.example.poki

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository(private val dao: PokemonDao) {


    suspend fun loadPokemons(limit: Int, offset: Int, query: String, types: List<String>): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            try {
                if (offset == 0) dao.clearAll() // Refresh full list if starting over
                val response = PokeApi.retrofitService.getPokemons(limit, offset)
                val pokemons = response.results.map { pokemon ->
                    // Fetch types for each (this might be inefficient, but for simplicity)
                    val detail = PokeApi.retrofitService.getPokemonDetail(pokemon.name)
                    val pokemonTypes = detail.types?.map { it.type.name } ?: emptyList() // Assume types field in PokemonDetail
                    Pokemon(
                        id = pokemon.url.split("/").dropLast(1).last(),
                        name = pokemon.name,
                        url = pokemon.url,
                        types = pokemonTypes
                    )
                }
                dao.insertAll(pokemons)
                filterPokemons(limit, offset, query, types)
            } catch (e: Exception) {
                // Offline: load from cache
                filterPokemons(limit, offset, query, types)
            }
        }
    }

    private suspend fun filterPokemons(limit: Int, offset: Int, query: String, types: List<String>): List<Pokemon> {
        val typesString = if (types.isNotEmpty()) types.joinToString(",") else null
        return dao.getPokemons(limit, offset, query, typesString)
    }
}


