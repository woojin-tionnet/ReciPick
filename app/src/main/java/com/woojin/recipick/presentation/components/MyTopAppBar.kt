package com.woojin.recipick.presentation.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.woojin.recipick.presentation.theme.mainColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    isNavigationAction: Boolean,
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (isNavigationAction) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = mainColor
        ),
        windowInsets = WindowInsets(0,0,0,0)
    )
}