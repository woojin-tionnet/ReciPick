package com.woojin.recipick.presentation.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.woojin.recipick.R
import com.woojin.recipick.domain.model.LoginResult

@SuppressLint("ContextCastToActivity")
@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val viewModel: SettingsViewModel = hiltViewModel()

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

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { viewModel.googleLogin(activity) }
        ) {
            Text(text = "로그인")
        }
        Button(
            onClick = { viewModel.googleLogout() }
        ) {
            Text(text = "로그아웃")
        }
    }
}