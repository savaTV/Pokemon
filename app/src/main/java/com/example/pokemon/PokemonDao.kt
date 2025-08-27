// app/src/main/java/com/example/pokemon/PokemonDao.kt
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

    @Query("SELECT * FROM pokemon")
    fun getAll(): Flow<List<Pokemon>>

    @Query("SELECT * FROM pokemon WHERE name LIKE :query OR id = :id OR type LIKE :type")
    fun search(query: String, id: Int = -1, type: String = ""): Flow<List<Pokemon>>
}