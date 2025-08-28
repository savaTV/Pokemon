// app/src/main/java/com/example/pokemon/PokemonViewModel.kt
package com.example.pokemon

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList: StateFlow<List<Pokemon>> = _pokemonList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _pokemonDetail = MutableStateFlow<Pokemon?>(null)
    val pokemonDetail: StateFlow<Pokemon?> = _pokemonDetail

    private var currentOffset = 0
    private val limit = 20
    private var currentName = ""
    private var currentType = ""
    private var totalPokemonCount = 200 // по умолчанию 200 покемонов, можно изменить

    init {
        viewModelScope.launch {
            totalPokemonCount = repository.getTotalPokemonCount()
        }
        loadPokemons()
    }

    fun loadPokemons(refresh: Boolean = false) {
        if (refresh) currentOffset = 0
        if (currentOffset >= totalPokemonCount) return // 👈 Не загружаем больше, чем доступно

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.loadPokemons(currentOffset, limit)
                when (result) {
                    is NetworkResult.Success -> {
                        if (refresh) {
                            _pokemonList.value = result.data
                        } else {
                            _pokemonList.value += result.data
                        }
                        currentOffset += limit
                    }
                    is NetworkResult.Error -> {
                        _errorMessage.value = result.message
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Неизвестная ошибка"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchPokemons(name: String, type: String) {
        currentName = name
        currentType = type
        viewModelScope.launch {
            repository.searchPokemons(name, type).firstOrNull()?.let {
                _pokemonList.value = it
            }
        }
    }

    fun loadPokemonDetail(id: Int) {
        viewModelScope.launch {
            repository.getPokemonById(id).firstOrNull()?.let {
                _pokemonDetail.value = it
            }
        }
    }

    fun refresh() {
        loadPokemons(refresh = true)
    }
}