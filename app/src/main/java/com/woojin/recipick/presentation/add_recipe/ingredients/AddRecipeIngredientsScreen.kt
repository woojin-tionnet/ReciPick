package com.woojin.recipick.presentation.add_recipe.ingredients

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.woojin.recipick.R
import com.woojin.recipick.data.local.model.FoodCategories
import com.woojin.recipick.presentation.home.HomeViewModel
import com.woojin.recipick.presentation.components.MyTopAppBar

@Composable
fun AddRecipeIngredientsScreen(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.add_recipe),
                true,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            val handleIngredientClick: (String) -> Unit = { ingredientName ->
                viewModel.selectedIngredientName(ingredientName)
                navController.popBackStack()
            }
            TitleAndRowItems(
                title = "육류",
                items = FoodCategories.meats,
                onIngredientClick = handleIngredientClick
            )
            TitleAndRowItems(
                title = "채소류",
                items = FoodCategories.vegetables,
                onIngredientClick = handleIngredientClick
            )
            TitleAndRowItems(
                title = "버섯",
                items = FoodCategories.mushRooms,
                onIngredientClick = handleIngredientClick
            )
            TitleAndRowItems(
                title = "유제품",
                items = FoodCategories.dairies,
                onIngredientClick = handleIngredientClick
            )
            TitleAndRowItems(
                title = "양념",
                items = FoodCategories.seasonings,
                onIngredientClick = handleIngredientClick
            )
            TitleAndRowItems(
                title = "곡류",
                items = FoodCategories.grains,
                onIngredientClick = handleIngredientClick
            )
            TitleAndRowItems(
                title = "기타",
                items = FoodCategories.etc,
                onIngredientClick = handleIngredientClick
            )
        }
    }
}