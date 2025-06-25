package com.woojin.recipick.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.woojin.recipick.presentation.home.add_recipe.detail.RecipeDetailScreen
import com.woojin.recipick.presentation.home.add_recipe.ingredients.AddRecipeIngredientsScreen
import com.woojin.recipick.presentation.home.add_recipe.steps.AddRecipeStepsScreen
import com.woojin.recipick.presentation.home.add_recipe.title_and_ingredients.AddRecipeTitleAndIngredients

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val homeNavController = rememberNavController()
    LaunchedEffect(key1 = Unit) {
        viewModel.navigateToScreen.collect { navigation ->
            when (navigation) {
                Screen.AddRecipeTitleAndIngredients -> homeNavController.navigate(Screen.AddRecipeTitleAndIngredients.route)
                Screen.AddRecipeIngredients -> homeNavController.navigate(Screen.AddRecipeIngredients.route)
                Screen.AddRecipeSteps -> homeNavController.navigate(Screen.AddRecipeSteps.route)
                Screen.Main -> homeNavController.navigate(Screen.Main.route) {
                    popUpTo(Screen.Main.route) { //메인 화면 까지 스택 제거
                        inclusive = true //메인 화면 자체도 스택 제거
                    }
                }

                Screen.RecipeDetail -> homeNavController.navigate(Screen.RecipeDetail.route)
            }
        }
    }
    NavHost(
        navController = homeNavController,
        startDestination = Screen.Main.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.Main.route) {
            MyRecipeMain(
                viewModel = viewModel,
                onClick = { viewModel.navUpdate(Screen.AddRecipeTitleAndIngredients) },
                mainItemClick = { recipeId ->
                    homeNavController.navigate(Screen.RecipeDetail.createRoute(recipeId))
                }
            )
        }

        composable(Screen.AddRecipeTitleAndIngredients.route) {
            val selectedIngredientName by viewModel.selectedIngredientName
            AddRecipeTitleAndIngredients(
                navController = homeNavController,
                selectedIngredientName = selectedIngredientName,
                clearSelectedIngredient = { viewModel.clearSelectedIngredientName() },
                addIngredientsClick = {
                    viewModel.navUpdate(Screen.AddRecipeIngredients)
                },
                onComplete = { data ->
                    viewModel.updateRecipeTitle(data.first)
                    viewModel.updateIngredients(data.second)
                    viewModel.navUpdate(Screen.AddRecipeSteps)
                }
            )
        }

        composable(Screen.AddRecipeIngredients.route) {
            AddRecipeIngredientsScreen(
                navController = homeNavController,
                viewModel = viewModel
            )
        }

        composable(Screen.AddRecipeSteps.route) {
            AddRecipeStepsScreen(
                navController = homeNavController,
                onClick = { viewModel.updateRecipeSteps(it) }
            )
        }

        composable(
            route = Screen.RecipeDetail.route,
            arguments = listOf(navArgument("recipeId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId")
            if (recipeId != null && recipeId != 0) {
                RecipeDetailScreen(
                    navController = homeNavController,
                    recipeId = recipeId
                )
            }
        }
    }

}