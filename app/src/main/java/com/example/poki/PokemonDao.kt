package com.example.poki

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.example.poki.Pokemon

@Dao
@TypeConverters(com.example.poki.TypeConverters::class)
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons: List<Pokemon>)

    @Query("SELECT * FROM pokemon WHERE name LIKE '%' || :query || '%' AND (:types IS NULL OR types LIKE '%' || :types || '%') LIMIT :limit OFFSET :offset")
    suspend fun getPokemons(limit: Int, offset: Int, query: String, types: String?): List<Pokemon>

    @Query("DELETE FROM pokemon")
    suspend fun clearAll()
}