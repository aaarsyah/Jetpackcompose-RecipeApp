package com.example.recipeapp.data

import com.example.recipeapp.model.FavRecipe
import com.example.recipeapp.model.RecipesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RecipeRepository {

    private val favRecipe = mutableListOf<FavRecipe>()

    init {
        if (favRecipe.isEmpty()) {
            RecipesData.recipes.forEach {
                favRecipe.add(FavRecipe(it, 0))
            }
        }
    }

    fun getAllRewards(): Flow<List<FavRecipe>> {
        return flowOf(favRecipe)
    }

    fun getFavRecipeById(recipeId: Long): FavRecipe {
        return favRecipe.first {
            it.recipe.id == recipeId
        }
    }

    fun searchRecipe(query: String): List<FavRecipe> {
        return favRecipe.filter {
            it.recipe.name.contains(query, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: RecipeRepository? = null

        fun getInstance(): RecipeRepository =
            instance ?: synchronized(this) {
                RecipeRepository().apply {
                    instance = this
                }
            }
    }
}