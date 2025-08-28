// app/src/main/java/com/example/pokemon/PokemonType.kt
package com.example.pokemon

import androidx.compose.ui.graphics.Color

enum class PokemonType(val typeName: String, val color1: Color, val color2: Color) {
    Normal("normal", Color(0xFFA8A77A), Color(0xFFDAD6C7)),
    Fire("fire", Color(0xFFFF6464), Color(0xFFFFAA33)),
    Water("water", Color(0xFF6390F0), Color(0xFF96D9F9)),
    Grass("grass", Color(0xFF7AC74C), Color(0xFFB5D66D)),
    Electric("electric", Color(0xFFF7D02C), Color(0xFFFFE138)),
    Ice("ice", Color(0xFF98D8D8), Color(0xFFB6F5F5)),
    Fighting("fighting", Color(0xFFC22E28), Color(0xFFED4456)),
    Poison("poison", Color(0xFFA33EA1), Color(0xFFD67FD8)),
    Ground("ground", Color(0xFFE2BF65), Color(0xFFF5D76E)),
    Flying("flying", Color(0xFFA98FF3), Color(0xFFC3B8F6)),
    Psychic("psychic", Color(0xFFF95587), Color(0xFFFF8BCD)),
    Bug("bug", Color(0xFFA6B91A), Color(0xFFC2D147)),
    Rock("rock", Color(0xFFB6A136), Color(0xFFD6C26E)),
    Ghost("ghost", Color(0xFF735797), Color(0xFFA38CD6)),
    Dragon("dragon", Color(0xFF6F35FC), Color(0xFF964BFF)),
    Dark("dark", Color(0xFF705746), Color(0xFFA08676)),
    Steel("steel", Color(0xFFB7B7CE), Color(0xFFDADADC)),
    Fairy("fairy", Color(0xFFD685AD), Color(0xFFF4C2F0));

    companion object {
        fun fromName(name: String): PokemonType? {
            return values().find { it.typeName == name.lowercase() }
        }
    }
}