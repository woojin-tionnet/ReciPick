package com.woojin.recipick.domain.usecase

import android.app.Activity
import com.woojin.recipick.domain.model.LoginResult
import com.woojin.recipick.domain.repository.LoginRepository
import javax.inject.Inject

class SignInGoogleUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        activity: Activity
    ): LoginResult {
        return loginRepository.signInGoogle(
            activity = activity
        )
    }
}