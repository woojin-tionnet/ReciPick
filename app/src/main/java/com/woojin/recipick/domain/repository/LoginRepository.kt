package com.woojin.recipick.domain.repository

import android.app.Activity
import com.woojin.recipick.domain.model.LoginResult

interface LoginRepository {
    suspend fun signInGoogle(activity: Activity): LoginResult
    suspend fun signOutGoogle(): LoginResult
}