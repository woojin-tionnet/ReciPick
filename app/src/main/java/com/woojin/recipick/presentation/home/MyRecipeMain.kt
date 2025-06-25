package com.woojin.recipick.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.woojin.recipick.R
import com.woojin.recipick.presentation.components.AlertNoTitleFunc
import com.woojin.recipick.presentation.components.FloatingButton
import com.woojin.recipick.presentation.components.MyTopAppBar
import com.woojin.recipick.presentation.components.RecipeMainItem

@Composable
fun MyRecipeMain(
    viewModel: HomeViewModel,
    onClick: () -> Unit,
    mainItemClick: (Int) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.main_title),
                false,
                onBackClick = {}
            )
        },
        floatingActionButton = {
            FloatingButton(
                onClick = onClick,
                iconString = "add"
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        val recipesState by viewModel.recipes.collectAsState() //저장된 레시피
        var showDeleteDialog by remember { mutableStateOf(false) } //삭제 확인 dialog 표시 여부
        var deleteIndex by remember { mutableIntStateOf(-1) } // 삭제 레시피 index 저장
        when {
            recipesState.isEmpty() -> {
                Text(
                    text = stringResource(R.string.no_saved_recipes),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center) // 텍스트의 세로 위치를 중앙으로 설정
                        .padding(16.dp),
                    textAlign = TextAlign.Center // 텍스트의 가로 위치를 중앙으로 설정
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = recipesState,
                        key = { recipe -> recipe.id ?: -1 }
                    ) { recipe ->
                        RecipeMainItem(
                            recipeTitle = recipe.title,
                            onItemClick = { recipe.id?.let(mainItemClick) },
                            onDeleteItemClick = {
                                showDeleteDialog = true
                                deleteIndex = recipe.id ?: -1
                            }
                        )
                    }
                }
            }
        }
        if (showDeleteDialog) {
            AlertNoTitleFunc(
                onDismissRequest = { showDeleteDialog = false },
                onConfirmation = {
                    viewModel.deleteRecipe(deleteIndex)
                    showDeleteDialog = false
                    deleteIndex = -1
                },
                dialogText = R.string.check_delete_item
            )
        }
    }
}