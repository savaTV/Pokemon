package com.example.pokeapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import java.util.ArrayList

class FilterActivity : AppCompatActivity() {

    private val types = listOf(
        "normal",
        "fire",
        "water",
        "grass",
        "electric",
        "ice",
        "fighting",
        "poison",
        "ground",
        "flying",
        "psychic",
        "bug",
        "rock",
        "ghost",
        "dark",
        "dragon",
        "steel",
        "fairy"
    )
    private val checkBoxes = mutableListOf<CheckBox>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        val filterLayout = findViewById<LinearLayout>(R.id.filterLayout)
        val applyButton = findViewById<Button>(R.id.applyButton)

        val selected = intent.getStringArrayListExtra("SELECTED_TYPES") ?: ArrayList()

        types.forEach { type ->
            val checkBox = CheckBox(this).apply {
                text = type.capitalize()
                isChecked = selected.contains(type)
            }
            filterLayout.addView(checkBox)
            checkBoxes.add(checkBox)
        }

        applyButton.setOnClickListener {
            val result = ArrayList<String>()
            checkBoxes.forEach { if (it.isChecked) result.add(it.text.toString().lowercase()) }
            val data = Intent().apply {
                putStringArrayListExtra("SELECTED_TYPES", result)
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        }

        // Back arrow in ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}