package com.woojin.recipick.domain.model

sealed class LoginResult {
    data class Success(
        val idToken: String
    ) : LoginResult()

    data class Failure(
        val errorMsg: String? = null, val exception: Throwable? = null
    ) : LoginResult()
}