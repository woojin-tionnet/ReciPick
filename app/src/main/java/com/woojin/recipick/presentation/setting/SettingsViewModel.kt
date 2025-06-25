package com.woojin.recipick.presentation.setting

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woojin.recipick.domain.model.LoginResult
import com.woojin.recipick.domain.usecase.SignInGoogleUseCase
import com.woojin.recipick.domain.usecase.SignOutGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val signInGoogleUseCase: SignInGoogleUseCase,
    private val signOutGoogleUseCase: SignOutGoogleUseCase
) : ViewModel() {
    private val _signInGoogleState = MutableSharedFlow<LoginResult>()
    val signInGoogleState: SharedFlow<LoginResult> = _signInGoogleState.asSharedFlow()
    private val _signOutGoogleState = MutableSharedFlow<LoginResult>()
    val signOutGoogleState: SharedFlow<LoginResult> = _signOutGoogleState.asSharedFlow()

    fun googleLogin(activity: Activity) {
        viewModelScope.launch {
            _signInGoogleState.emit(signInGoogleUseCase(activity))
        }
    }

    fun googleLogout() {
        viewModelScope.launch {
            _signOutGoogleState.emit(signOutGoogleUseCase())
        }
    }
}