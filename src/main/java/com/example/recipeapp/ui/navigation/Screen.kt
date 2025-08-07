package com.example.recipeapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object DetailRecipe : Screen("home/{recipeId}") {
        fun createRoute(recipeId: Long) = "home/$recipeId"
    }
}