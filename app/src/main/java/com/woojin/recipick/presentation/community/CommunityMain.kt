package com.woojin.recipick.presentation.community

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.woojin.recipick.R
import com.woojin.recipick.presentation.components.MyTopAppBar
import com.woojin.recipick.state.UiState

@Composable
fun CommunityMain(
    viewModel: CommunityViewModel
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.randomRecipeState.collect { state ->
            when (state) {
                is UiState.Success -> {
                    val result = state.data
                    Toast.makeText(context, result.recipes[0].title, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.searchRecipeState.collect { state ->
            when (state) {
                is UiState.Success -> {
                    val result = state.data
                    Toast.makeText(context, result.results[0].title, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.community_title),
                false,
                onBackClick = {}
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "준비중 입니다.", fontSize = 24.sp)
            Button(
                onClick = { viewModel.requestRandomRecipe() }
            ) {
                Text("랜덤 제목")
            }
            Button(
                onClick = { viewModel.searchRecipe("pasta") }
            ) {
                Text("pasta 검색")
            }
            Text(
                "안녕~~~~~ 하세요오~"
            )
        }
    }
}