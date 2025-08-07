package com.example.recipeapp.di

import com.example.recipeapp.data.RecipeRepository


object Injection {
    fun provideRepository(): RecipeRepository {
        return RecipeRepository.getInstance()
    }
}