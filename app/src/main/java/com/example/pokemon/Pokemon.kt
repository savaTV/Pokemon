package com.example.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pokemon")
data class Pokemon(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String,
    val imageUrl: String
)