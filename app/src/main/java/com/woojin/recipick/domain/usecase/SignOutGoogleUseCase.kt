package com.woojin.recipick.domain.usecase

import com.woojin.recipick.domain.model.LoginResult
import com.woojin.recipick.domain.repository.LoginRepository
import javax.inject.Inject

class SignOutGoogleUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): LoginResult {
        return loginRepository.signOutGoogle()
    }
}