package com.woojin.recipick.presentation.home.add_recipe.steps

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.woojin.recipick.R

@Composable
fun StepInputField(
    stepNumber: Int,
    text: String,
    onTextChange: (String) -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$stepNumber.",
            modifier = Modifier.padding(end = 8.dp)
        )
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            label = { Text(stringResource(R.string.recipe_step_hint, stepNumber)) },
            modifier = Modifier.weight(1f),
            singleLine = false
        )
        // 삭제 아이콘 버튼 추가
        IconButton(onClick = onDeleteClick) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "조리 과정 삭제",
            )
        }
    }
}
