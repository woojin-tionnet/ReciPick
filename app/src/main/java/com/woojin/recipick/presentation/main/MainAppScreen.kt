package com.woojin.recipick.presentation.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.woojin.recipick.presentation.community.CommunityScreen
import com.woojin.recipick.presentation.home.HomeScreen
import com.woojin.recipick.presentation.setting.SettingsScreen

@Composable
fun MainAppScreen(
    activity: MainActivity
) {
    val navController = rememberNavController()
    val navItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Community,
        BottomNavItem.Settings
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                windowInsets = WindowInsets.navigationBars
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                navItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // 백 스택의 시작점까지 pop하여 동일한 목적지를 여러 번 쌓지 않도록 함
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // 동일한 아이템을 다시 선택했을 때 새 인스턴스를 만들지 않도록 함
                                launchSingleTop = true
                                // 이전에 선택했던 아이템으로 돌아갈 때 상태 복원
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPaddingFromMainActivity ->
        // NavHost 가 화면의 메인 컨텐츠 영역을 차지
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route, // 시작 화면 경로
            modifier = Modifier.padding(innerPaddingFromMainActivity) // Scaffold 패딩 적용
        ) {
            composable(BottomNavItem.Home.route) { HomeScreen() }
            composable(BottomNavItem.Community.route) { CommunityScreen() }
            composable(BottomNavItem.Settings.route) { SettingsScreen() }
        }
    }
}