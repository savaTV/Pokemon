package com.example.pokemon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons: List<Pokemon>)

    @Query("SELECT * FROM Pokemon")
    fun getAllPokemons(): Flow<List<Pokemon>>

    @Query("SELECT * FROM Pokemon WHERE name LIKE :name AND type LIKE :type")
    fun searchByNameAndType(name: String, type: String): Flow<List<Pokemon>>

    @Query("SELECT * FROM Pokemon WHERE id = :id")
    fun getPokemonById(id: Int): Flow<Pokemon?>
}