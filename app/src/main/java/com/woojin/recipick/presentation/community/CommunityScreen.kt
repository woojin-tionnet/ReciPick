package com.woojin.recipick.presentation.community

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun CommunityScreen() {
    val viewModel: CommunityViewModel = hiltViewModel()
    val communityNavController = rememberNavController()

    LaunchedEffect(key1 = Unit) {
        viewModel.navigateToScreen.collect { navigation ->
            when (navigation) {
                Screen.Main -> communityNavController.navigate(Screen.Main.route)
                Screen.Sub -> communityNavController.navigate(Screen.Sub.route)
            }
        }
    }
    NavHost(
        navController = communityNavController,
        startDestination = Screen.Main.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.Main.route) {
            CommunityMain(
                viewModel = viewModel
            )
        }

        composable(Screen.Sub.route) {
            CommunityMain(
                viewModel = viewModel
            )
        }
    }
}