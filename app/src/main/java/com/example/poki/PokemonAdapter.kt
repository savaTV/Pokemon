package com.example.poki

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokeapp.R

class PokemonAdapter(private val onClick: (Pokemon) -> Unit) : ListAdapter<Pokemon, PokemonAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.pokemonName)
        val image: ImageView = view.findViewById(R.id.pokemonImage)
        val cardView: androidx.cardview.widget.CardView = view.findViewById(R.id.cardView)
        val contentContainer: androidx.constraintlayout.widget.ConstraintLayout = view.findViewById(R.id.content_container)  // ← добавлено
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.name.text = pokemon.name.capitalize()
        Glide.with(holder.itemView.context).load(pokemon.imageUrl).into(holder.image)

        holder.itemView.setOnClickListener { onClick(pokemon) }

        // Получаем базовый цвет по типу
        val baseColor = when (pokemon.types.firstOrNull()) {
            "grass" -> ContextCompat.getColor(holder.itemView.context, R.color.color_grass)
            "fire" -> ContextCompat.getColor(holder.itemView.context, R.color.color_fire)
            "water" -> ContextCompat.getColor(holder.itemView.context, R.color.color_water)
            "electric" -> ContextCompat.getColor(holder.itemView.context, R.color.color_electric)
            "ground" -> ContextCompat.getColor(holder.itemView.context, R.color.color_ground)
            "rock" -> ContextCompat.getColor(holder.itemView.context, R.color.color_rock)
            "ghost" -> ContextCompat.getColor(holder.itemView.context, R.color.color_ghost)
            "bug" -> ContextCompat.getColor(holder.itemView.context, R.color.color_bug)
            "steel" -> ContextCompat.getColor(holder.itemView.context, R.color.color_steel)
            "psychic" -> ContextCompat.getColor(holder.itemView.context, R.color.color_psychic)
            "ice" -> ContextCompat.getColor(holder.itemView.context, R.color.color_ice)
            "dragon" -> ContextCompat.getColor(holder.itemView.context, R.color.color_dragon)
            "dark" -> ContextCompat.getColor(holder.itemView.context, R.color.color_dark)
            "fairy" -> ContextCompat.getColor(holder.itemView.context, R.color.color_fairy)
            else -> ContextCompat.getColor(holder.itemView.context, R.color.default_color)
        }

        // Делаем CardView прозрачным
        holder.cardView.setCardBackgroundColor(Color.TRANSPARENT)

        // Создаем темный вариант цвета (коэффициент 0.7 для затемнения; можно изменить)
        val red = (Color.red(baseColor) * 0.4f).toInt().coerceIn(0, 255)
        val green = (Color.green(baseColor) * 0.4f).toInt().coerceIn(0, 255)
        val blue = (Color.blue(baseColor) * 0.4f).toInt().coerceIn(0, 255)
        val darkColor = Color.rgb(red, green, blue)

        // Создаем градиент от базового цвета сверху к темному снизу
        val gradient = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(baseColor, darkColor))
        gradient.shape = GradientDrawable.RECTANGLE  // Прямоугольник, CardView обрежет углы
         // Опционально: синхронизируем радиус углов (если нужно)

        // Устанавливаем градиент на внутренний контейнер
        holder.contentContainer.background = gradient
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean = oldItem.name == newItem.name
            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean = oldItem == newItem
        }
    }
}