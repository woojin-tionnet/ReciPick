package com.woojin.recipick.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.woojin.recipick.presentation.theme.mainColor


@Composable
fun FloatingButton(
    onClick: () -> Unit,
    iconString: String
) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = mainColor
    ) {
        when (iconString) {
            "add" -> Icon(Icons.Filled.Add, "레시피 추가 버튼")
            "edit" -> Icon(Icons.Filled.Edit, "레시피 수정 버튼")
            "save" -> Icon(Icons.Filled.Done, "레시피 저장 버튼")
        }
    }
}
