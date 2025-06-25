package com.woojin.recipick.presentation.home.add_recipe.detail

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.woojin.recipick.R
import com.woojin.recipick.data.local.entity.RecipeEntity
import com.woojin.recipick.presentation.components.AlertNoTitleFunc
import com.woojin.recipick.presentation.components.FloatingButton
import com.woojin.recipick.presentation.components.MyTopAppBar
import com.woojin.recipick.presentation.theme.RecipickTheme

@Composable
fun RecipeDetailScreen(
    navController: NavHostController,
    recipeId: Int,
) {
    val recipeDetailViewModel: RecipeDetailViewModel = hiltViewModel()
    LaunchedEffect(key1 = recipeId) {
        if (recipeId != 0) {
            recipeDetailViewModel.recipeDetail(recipeId)
        }
    }
    val recipeDetailData by recipeDetailViewModel.uiState.collectAsState()
    RecipeDetail(
        navController = navController,
        recipeDetailUiState = recipeDetailData,
        saveRecipeBtn = { data ->
            recipeDetailViewModel.updateRecipe(data = data)
        },
        deleteIngredient = { index ->
            recipeDetailViewModel.deleteIngredient(index = index)
        },
        deleteStep = { index ->
            recipeDetailViewModel.deleteStep(index = index)
        },
        updateEditMode = { recipeDetailViewModel.updateEditMode(it) },
        updateEditTitle = { recipeDetailViewModel.updateEditTitle(it) },
        updateEditIngredients = { recipeDetailViewModel.updateEditIngredients(it) },
        updateEditSteps = { recipeDetailViewModel.updateEditSteps(it) },
        updateDeleteIngredientDialog = { recipeDetailViewModel.updateDeleteIngredientDialog(it) },
        updateDeleteStepDialog = { recipeDetailViewModel.updateDeleteStepDialog(it) },
    )
}

@Composable
fun RecipeDetail(
    navController: NavHostController,
    recipeDetailUiState: RecipeDetailUiState,
    saveRecipeBtn: (RecipeEntity) -> Unit,
    deleteIngredient: (Int) -> Unit,
    deleteStep: (Int) -> Unit,
    updateEditMode: (Boolean) -> Unit,
    updateEditTitle: (String) -> Unit,
    updateEditIngredients: (List<String>) -> Unit,
    updateEditSteps: (List<String>) -> Unit,
    updateDeleteIngredientDialog: (Pair<Boolean, Int>) -> Unit,
    updateDeleteStepDialog: (Pair<Boolean, Int>) -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.recipe_detail_title),
                true,
                onBackClick = {
                    updateEditMode(false)
                    navController.popBackStack()
                }
            )
        },
        floatingActionButton = {
            FloatingButton(
                onClick = {
                    val text = when {
                        recipeDetailUiState.editTitle.isBlank() -> R.string.please_input_title
                        recipeDetailUiState.editIngredients.any { it.isBlank() } -> R.string.please_input_ingredient
                        recipeDetailUiState.editSteps.any { it.isBlank() } -> R.string.please_input_step
                        else -> null
                    }
                    if (text != null) {
                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                    } else {
                        if (recipeDetailUiState.isEditMode) {
                            saveRecipeBtn(
                                RecipeEntity(
                                    recipeDetailUiState.recipeEntity.id,
                                    recipeDetailUiState.editTitle,
                                    recipeDetailUiState.editIngredients,
                                    recipeDetailUiState.editSteps
                                )
                            )
                        }
                        updateEditMode(!recipeDetailUiState.isEditMode)
                    }
                },
                iconString = if (recipeDetailUiState.isEditMode) "save" else "edit"
            )
        }
    ) { innerPadding ->
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                // 레시피 제목
                item {
                    if (recipeDetailUiState.isEditMode) {
                        OutlinedTextField(
                            value = recipeDetailUiState.editTitle,
                            onValueChange = { updateEditTitle(it) },
                            label = { Text(stringResource(R.string.recipe_title_edit_text)) },
                            singleLine = true,
                        )
                    } else {
                        Text(
                            text = recipeDetailUiState.recipeEntity.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }

                // 재료 아이템
                item {
                    RecipeDetailListItem(
                        titleResID = R.string.ingredients_section_title,
                        isEditMode = recipeDetailUiState.isEditMode,
                        items = recipeDetailUiState.editIngredients,
                        updateDeleteItemDialog = { index ->
                            updateDeleteIngredientDialog(Pair(true, index))
                        },
                        updateEditItems = { updateEditIngredients(it) },
                        onAddItem = {
                            val newList = recipeDetailUiState.editIngredients.toMutableList()
                            newList.add("") // 빈 문자열 추가 또는 "새 재료" 등 기본값
                            updateEditIngredients(newList)
                        },
                        addButtonTextResId = R.string.add_recipe,
                        emptyListMessageResId = R.string.no_ingredients_message,
                    )
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }

                // 조리 단계 아이템
                item {
                    RecipeDetailListItem(
                        titleResID = R.string.steps_section_title,
                        isEditMode = recipeDetailUiState.isEditMode,
                        items = recipeDetailUiState.editSteps,
                        updateDeleteItemDialog = { index ->
                            updateDeleteStepDialog(Pair(true, index))
                        },
                        updateEditItems = { updateEditSteps(it) },
                        onAddItem = {
                            val newList = recipeDetailUiState.editSteps.toMutableList()
                            newList.add("") // 빈 문자열 추가 또는 "새 재료" 등 기본값
                            updateEditSteps(newList)
                        },
                        addButtonTextResId = R.string.add_recipe_step_button,
                        emptyListMessageResId = R.string.no_steps_message,
                    )
                }
            }

            when {
                recipeDetailUiState.showDeleteIngredientDialog && recipeDetailUiState.deleteIngredientIndex != -1 -> {
                    AlertNoTitleFunc(
                        onDismissRequest = { updateDeleteIngredientDialog(Pair(false, -1)) },
                        onConfirmation = {
                            deleteIngredient(recipeDetailUiState.deleteIngredientIndex)
                            updateDeleteIngredientDialog(Pair(false, -1))
                        },
                        dialogText = R.string.check_delete_item
                    )
                }

                recipeDetailUiState.showDeleteStepDialog && recipeDetailUiState.deleteStepIndex != -1 -> {
                    AlertNoTitleFunc(
                        onDismissRequest = { updateDeleteStepDialog(Pair(false, -1)) },
                        onConfirmation = {
                            deleteStep(recipeDetailUiState.deleteStepIndex)
                            updateDeleteStepDialog(Pair(false, -1))
                        },
                        dialogText = R.string.check_delete_item
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailPreview() {
    RecipickTheme {
        RecipeDetail(
            navController = rememberNavController(),
            recipeDetailUiState = RecipeDetailUiState(
                recipeEntity = RecipeEntity(
                    1,
                    "레시피제목",
                    listOf("양파1개", "대파1개"),
                    listOf("재료넣고", "볶기")
                ),
                isEditMode = true
            ),
            saveRecipeBtn = {},
            deleteIngredient = { _ -> },
            deleteStep = { _ -> },
            updateEditMode = {},
            updateEditTitle = {},
            updateEditIngredients = {},
            updateEditSteps = {},
            updateDeleteIngredientDialog = {},
            updateDeleteStepDialog = {}
        )
    }
}