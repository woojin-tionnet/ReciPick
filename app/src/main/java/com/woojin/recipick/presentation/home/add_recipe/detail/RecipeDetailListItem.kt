package com.woojin.recipick.presentation.home.add_recipe.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import com.woojin.recipick.R
import com.woojin.recipick.presentation.theme.RecipickTheme

@Composable
fun RecipeDetailListItem(
    titleResID: Int,
    isEditMode: Boolean,
    items: List<String>,
    updateDeleteItemDialog: (Int) -> Unit,
    updateEditItems: (List<String>) -> Unit,
    onAddItem: () -> Unit,
    addButtonTextResId: Int,
    emptyListMessageResId: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        //부제목
        Text(
            text = stringResource(titleResID),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (isEditMode) {
            //수정
            items.fastForEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { updateDeleteItemDialog(index) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "삭제 아이콘",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(20.dp),
                        )
                    }
                    TextField(
                        value = item,
                        onValueChange = { newValue ->
                            val newList = items.toMutableList()
                            newList[index] = newValue
                            updateEditItems(newList)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // 항목 추가 버튼
            OutlinedButton(
                onClick = onAddItem,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.Add, contentDescription = "추가 버튼")
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(addButtonTextResId))
            }
        } else {
            // 보기 모드 UI
            if (items.isNotEmpty()) {
                items.forEach { item ->
                    Text(
                        text = "- $item",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp, start = 8.dp)
                    )
                }
            } else {
                Text(
                    text = stringResource(emptyListMessageResId),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp, start = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailListItemPreview() {
    RecipickTheme {
        RecipeDetailListItem(
            titleResID = R.string.ingredients_section_title,
            isEditMode = true,
            items = listOf("양파3개", "당근1개"),
            updateDeleteItemDialog = {},
            updateEditItems = {},
            onAddItem = {},
            addButtonTextResId = R.string.add_recipe,
            emptyListMessageResId = R.string.no_ingredients_message
        )
    }
}