package com.example.poki

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey val id: String,
    val name: String,
    val url: String,
    val imageUrl: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${url.split("/").dropLast(1).last()}.png",
    val types: List<String> = emptyList()

)

// model/PokemonResponse.kt
data class PokemonResponse(
    val results: List<Pokemon>
)

// model/PokemonDetail.kt
data class PokemonDetail(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("sprites") val sprites: Sprites,
    @SerializedName("types") val types: List<TypeEntry>? = null
)

data class Sprites(
    @SerializedName("front_default") val frontDefault: String
)

data class TypeEntry(
    @SerializedName("type") val type: Type
)

data class Type(
    @SerializedName("name") val name: String
)