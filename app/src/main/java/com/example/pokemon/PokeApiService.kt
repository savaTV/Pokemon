// app/src/main/java/com/example/pokemon/PokeApiService.kt
package com.example.pokemon

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonListResponse(
    val results: List<PokemonEntry>,
    @Json(name = "next") val nextUrl: String?
)

@JsonClass(generateAdapter = true)
data class PokemonEntry(
    val name: String,
    @Json(name = "url") val url: String
)

@JsonClass(generateAdapter = true)
data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    @Json(name = "types") val types: List<TypeEntry>
)

@JsonClass(generateAdapter = true)
data class TypeEntry(
    @Json(name = "type") val type: Type
)

@JsonClass(generateAdapter = true)
data class Type(
    val name: String
)

fun getUrlFromUrl(url: String): String {
    val parts = url.split("/")
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${parts[6]}.png"
}

object PokeApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val service = retrofit.create(PokeApi::class.java)

    interface PokeApi {
        @GET("pokemon")
        suspend fun listPokemons(@Query("offset") offset: Int, @Query("limit") limit: Int): PokemonListResponse

        @GET("pokemon/{name}")
        suspend fun getPokemonDetails(@Path("name") name: String): PokemonDetailResponse
    }

    suspend fun fetchPokemons(offset: Int = 0, limit: Int = 20): List<Pokemon> {
        val response = withContext(Dispatchers.IO) { service.listPokemons(offset, limit) }
        return response.results.mapIndexed { index, entry ->
            val id = index + offset + 1
            val detailResponse = withContext(Dispatchers.IO) { service.getPokemonDetails(entry.name) }
            val types = detailResponse.types.joinToString(", ") { it.type.name }

            Pokemon(
                id = id,
                name = entry.name,
                type = types,
                imageUrl = getUrlFromUrl(entry.url)
            )
        }
    }
}