package com.woojin.recipick.presentation.setting

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@SuppressLint("ContextCastToActivity")
@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = hiltViewModel()
    val settingsNavController = rememberNavController()

    LaunchedEffect(key1 = Unit) {
        viewModel.navigateToScreen.collect { navigation ->
            when (navigation) {
                Screen.Main -> settingsNavController.navigate(Screen.Main.route)
                Screen.Sub -> settingsNavController.navigate(Screen.Sub.route)
            }
        }
    }

    NavHost(
        navController = settingsNavController,
        startDestination = Screen.Main.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.Main.route) {
            SettingsMain(
                viewModel = viewModel
            )
        }

        composable(Screen.Sub.route) {
            SettingsMain(
                viewModel = viewModel
            )
        }
    }
}