package com.woojin.recipick.presentation.home

sealed class Screen(val route: String) {
    object Main: Screen("main_screen")
    object AddRecipeTitleAndIngredients: Screen("add_recipe_title_and_ingredients_screen")
    object AddRecipeIngredients: Screen("add_recipe_ingredients_screen")
    object AddRecipeSteps: Screen("add_recipe_steps_screen")
    object RecipeDetail : Screen("recipe_detail_screen/{recipeId}") {
        fun createRoute(recipeId: Int) = "recipe_detail_screen/$recipeId"
    }
}