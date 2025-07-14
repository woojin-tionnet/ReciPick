package com.woojin.recipick.presentation.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.woojin.recipick.domain.model.LoginResult
import com.woojin.recipick.presentation.components.MyTopAppBar

@SuppressLint("ContextCastToActivity")
@Composable
fun SettingsMain(
    viewModel: SettingsViewModel
) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    LaunchedEffect(key1 = Unit) {
        viewModel.signInGoogleState.collect {
            when (it) {
                is LoginResult.Success -> {
                    Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show()
                }

                is LoginResult.Failure -> {
                    Toast.makeText(context, R.string.login_fail, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.signOutGoogleState.collect {
            when (it) {
                is LoginResult.Success -> {
                    Toast.makeText(context, R.string.logout_success, Toast.LENGTH_SHORT).show()
                }

                is LoginResult.Failure -> {
                    Toast.makeText(context, R.string.logout_fail, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.settings_title),
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
//        Button(
//            onClick = { viewModel.googleLogin(activity) }
//        ) {
//            Text(text = "로그인")
//        }
//        Button(
//            onClick = { viewModel.googleLogout() }
//        ) {
//            Text(text = "로그아웃")
//        }
            Text(text = "준비중 입니다.", fontSize = 24.sp)
        }
    }
}