package com.woojin.recipick.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.woojin.recipick.domain.model.LoginResult
import com.woojin.recipick.presentation.add_recipe.detail.RecipeDetailScreen
import com.woojin.recipick.presentation.add_recipe.ingredients.AddRecipeIngredientsScreen
import com.woojin.recipick.presentation.add_recipe.steps.AddRecipeStepsScreen
import com.woojin.recipick.presentation.add_recipe.title_and_ingredients.AddRecipeTitleAndIngredients
import com.woojin.recipick.presentation.navigation.Screen
import com.woojin.recipick.presentation.theme.RecipickTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipickTheme {
                val navController = rememberNavController()
                LaunchedEffect(key1 = Unit) {
                    viewModel.navigateToScreen.collect { navigation ->
                        when (navigation) {
                            Screen.AddRecipeTitleAndIngredients -> navController.navigate(Screen.AddRecipeTitleAndIngredients.route)
                            Screen.AddRecipeIngredients -> navController.navigate(Screen.AddRecipeIngredients.route)
                            Screen.AddRecipeSteps -> navController.navigate(Screen.AddRecipeSteps.route)
                            Screen.Main -> navController.navigate(Screen.Main.route) {
                                popUpTo(Screen.Main.route) { //메인 화면 까지 스택 제거
                                    inclusive = true //메인 화면 자체도 스택 제거
                                }
                            }

                            Screen.RecipeDetail -> navController.navigate(Screen.RecipeDetail.route)
                        }
                    }
                }
                LaunchedEffect(key1 = Unit) {
                    viewModel.signInGoogleState.collect {
                        when(it) {
                            is LoginResult.Success -> {
                                Toast.makeText(this@MainActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                            }
                            is LoginResult.Failure -> {
                                Toast.makeText(this@MainActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                            }
                            else -> {}
                        }
                    }
                }
                NavHost(
                    navController = navController,
                    startDestination = Screen.Main.route
                ) {
                    composable(Screen.Main.route) {
                        AppScreen(
                            viewModel = viewModel,
                            onClick = { viewModel.navUpdate(Screen.AddRecipeTitleAndIngredients) },
                            mainItemClick = { recipeId ->
                                navController.navigate(Screen.RecipeDetail.createRoute(recipeId))
                            },
                            signInButton = { viewModel.googleLogin(this@MainActivity) },
                            signOutButton = { viewModel.googleLogout() }
                        )
                    }

                    composable(Screen.AddRecipeTitleAndIngredients.route) {
                        val selectedIngredientName by viewModel.selectedIngredientName
                        AddRecipeTitleAndIngredients(
                            navController = navController,
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
                            navController = navController,
                            viewModel = viewModel
                        )
                    }

                    composable(Screen.AddRecipeSteps.route) {
                        AddRecipeStepsScreen(
                            navController = navController,
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
                                navController = navController,
                                recipeId = recipeId
                            )
                        }
                    }
                }
            }
        }
    }
}