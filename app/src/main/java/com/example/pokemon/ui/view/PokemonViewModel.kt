// app/src/main/java/com/example/pokemon/PokemonViewModel.kt
package com.example.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val _pokemonList = MutableLiveData<List<Pokemon>>(emptyList())
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadPokemons() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.loadPokemons()
                when (result) {
                    is NetworkResult.Success -> {
                        _pokemonList.value = result.data
                    }
                    is NetworkResult.Error -> {
                        _errorMessage.value = result.message
                    }
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchPokemons(name: String, type: String) {
        viewModelScope.launch {
            val results = repository.searchPokemons("$name $type").firstOrNull()
            _pokemonList.value = results ?: emptyList()
        }
    }
}