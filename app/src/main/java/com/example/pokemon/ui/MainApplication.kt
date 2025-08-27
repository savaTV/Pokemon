// app/src/main/java/com/example/pokemon/MainApplication.kt
package com.example.pokemon

import android.app.Application
import com.example.pokemon.AppDatabase

class MainApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
}