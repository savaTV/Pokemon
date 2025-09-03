package com.example.poki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {

    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>> = _pokemons

    private val _isNetworkAvailable = MutableLiveData<Boolean>()
    val isNetworkAvailable: LiveData<Boolean> = _isNetworkAvailable

    fun loadPokemons(limit: Int, offset: Int, query: String, types: List<String>) {
        viewModelScope.launch {
            val list = repository.loadPokemons(limit, offset, query, types)
            val current = _pokemons.value ?: emptyList()
            _pokemons.value = if (offset == 0) list else current + list
            _isNetworkAvailable.value = true // Assume network check
        }
    }
}