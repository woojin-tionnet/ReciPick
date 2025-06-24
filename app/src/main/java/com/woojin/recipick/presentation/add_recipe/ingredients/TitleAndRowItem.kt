package com.woojin.recipick.presentation.add_recipe.ingredients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woojin.recipick.data.local.model.FoodCategories
import com.woojin.recipick.presentation.theme.RecipickTheme

@Composable
fun TitleAndRowItems(
    title: String,
    items: List<String>,
    onIngredientClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp), // 좌우 패딩
            horizontalArrangement = Arrangement.spacedBy(8.dp) // 아이템 간 간격
        ) {
            items(items) { ingredient ->
                FilterChip(
                    selected = false,
                    onClick = { onIngredientClick(ingredient) },
                    label = { Text(ingredient) },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = MaterialTheme.colorScheme.outline,
                        borderWidth = 1.dp,
                        enabled = true,
                        selected = false
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TitleAndRowItemsPreview() {
    RecipickTheme {
        TitleAndRowItems(
            title = "제목입니다",
            items = FoodCategories.meats,
            onIngredientClick = {}
        )
    }
}