package com.woojin.recipick.presentation.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.woojin.recipick.presentation.theme.RecipickTheme

@Composable
fun RecipeMainItem(
    recipeTitle: String,
    onItemClick: () -> Unit,
    onDeleteItemClick: () -> Unit
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ){
        OutlinedButton(
            onClick = { onItemClick() },
            modifier = Modifier.weight(1f)
        ) {
            Text(recipeTitle)
        }

        IconButton(onClick = { onDeleteItemClick() }) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete Recipe"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainItemPreview() {
    RecipickTheme {
        RecipeMainItem(
            recipeTitle = "명란파스타",
            onItemClick = {},
            onDeleteItemClick = {}
        )
    }
}