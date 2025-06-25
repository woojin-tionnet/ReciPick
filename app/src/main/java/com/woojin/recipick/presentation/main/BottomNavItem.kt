package com.woojin.recipick.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem (
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "홈", Icons.Filled.Home)
    object Community: BottomNavItem("community", "커뮤니티", Icons.Filled.AccountCircle)
    object Settings: BottomNavItem("setting", "설정", Icons.Filled.AccountCircle)
}