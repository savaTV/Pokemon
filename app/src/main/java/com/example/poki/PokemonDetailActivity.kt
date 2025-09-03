package com.example.poki

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.pokeapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonDetailActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail) // ← ИСПРАВЛЕНО!

        val name = intent.getStringExtra("POKEMON_NAME") ?: return
        val imageView = findViewById<ImageView>(R.id.detailImage)
        val nameText = findViewById<TextView>(R.id.detailName)
        val heightText = findViewById<TextView>(R.id.detailHeight)
        val weightText = findViewById<TextView>(R.id.detailWeight)

        nameText.text = name.capitalize()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val detail = PokeApi.retrofitService.getPokemonDetail(name)
                withContext(Dispatchers.Main) {
                    Glide.with(this@PokemonDetailActivity).load(detail.sprites.frontDefault).into(imageView)
                    heightText.text = "Height: ${detail.height / 10.0} m"
                    weightText.text = "Weight: ${detail.weight / 10.0} kg"
                }
            } catch (e: Exception) {
                // Можно добавить лог или уведомление пользователю
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}