package com.example.recipeapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.FavRecipe
import com.example.recipeapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class HomeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FavRecipe>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<FavRecipe>>>
        get() = _uiState

    fun getAllRewards() {
        viewModelScope.launch {
            repository.getAllRewards()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { favRecipe ->
                    _uiState.value = UiState.Success(favRecipe)
                }
        }
    }

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            try {
                val searchResults = repository.searchRecipe(_query.value)
                    .sortedBy { it.recipe.name }

                _uiState.value = UiState.Success(searchResults)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }
}