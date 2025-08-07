package com.example.recipeapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.FavRecipe
import com.example.recipeapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailRecipeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavRecipe>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavRecipe>>
        get() = _uiState

    fun getRewardById(recipeId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getFavRecipeById(recipeId))
        }
    }
}