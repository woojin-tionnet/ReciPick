package com.woojin.recipick.presentation.setting

sealed class Screen(val route: String) {
    object Main: Screen("main_screen")
    object Sub: Screen("sub_screen")
}