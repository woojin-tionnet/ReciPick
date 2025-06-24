package com.woojin.recipick.data.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.woojin.recipick.R
import com.woojin.recipick.domain.model.LoginResult
import com.woojin.recipick.domain.repository.LoginRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.SecureRandom
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : LoginRepository {

    private companion object {
        private val TAG = LoginRepositoryImpl::class.java.simpleName
    }

    private fun generateNonce(): String {
        val random = SecureRandom()
        val nonceBytes = ByteArray(16) // 16 바이트 (128 비트)
        random.nextBytes(nonceBytes)
        // 각 바이트를 두 자리 16진수 문자열로 변환하고 모두 이어붙입니다.
        return nonceBytes.joinToString("") { "%02x".format(it) }
    }

    override suspend fun signInGoogle(
        activity: Activity
    ): LoginResult {
        return try {
            val credentialManager = CredentialManager.create(activity)
            val webClientId = applicationContext.getString(R.string.google_web_client_id)
            val nonce = generateNonce()

            // 1. GetGoogleIdOption 구성
            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true) // 이전에 이 앱에서 로그인한 적 있는 계정 우선 표시
                .setServerClientId(webClientId)      // 웹 애플리케이션의 클라이언트 ID
                .setNonce(nonce)                     // Replay 공격 방지를 위한 Nonce
                .setAutoSelectEnabled(true)          // 조건 충족 시 계정 자동 선택 UI 표시
                // .setLinkedServiceId("your-service-id") // 서비스 ID (선택 사항)
                // .setIdTokenDepositionStrategy(IdTokenDepositionStrategy.REMOTE_SERVER) // ID 토큰 저장 전략 (선택 사항)
                .build()

            // 2. GetCredentialRequest 생성
            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            // CredentialManager.getCredential() 은 UI를 표시 하므로 Main 스레드 에서 실행
            val result = withContext(Dispatchers.Main) {
                credentialManager.getCredential(activity, request)
            }
            val credential = result.credential

            if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                try {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    val googleIdToken = googleIdTokenCredential.idToken
                    Log.d(TAG, "Google ID Token: $googleIdToken")
                    if (googleIdToken.isNotBlank()) {
                        // 성공 시, ID 토큰을 로컬에 저장할 수 있음 (예: authLocalDataSource.saveIdToken(googleIdToken))
                        LoginResult.Success(googleIdToken)
                    } else {
                        Log.e(TAG, "Google ID Token is null or empty.")
                        LoginResult.Failure(errorMsg = "Google ID Token is null or empty.")
                    }
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e(TAG, "Failed to parse Google ID token from credential data.", e)
                    LoginResult.Failure(errorMsg = "Failed to parse Google ID token.", exception = e)
                }
            } else {
                Log.e(TAG, "Unexpected credential type or not a CustomCredential: ${credential.type}")
                LoginResult.Failure(errorMsg = "Unexpected credential type: ${credential.type}")
            }
        } catch (e: GetCredentialException) {
            Log.e(TAG, "GetCredentialException: ${e.message}, Type: ${e.type}", e)
            LoginResult.Failure(errorMsg = "Login failed: ${e.message}", exception = e)
        } catch (e: Exception) { // 모든 종류의 예외를 잡기 위함
            Log.e(TAG, "An unexpected error occurred during sign-in.", e)
            LoginResult.Failure(errorMsg = "An unexpected error occurred.", exception = e)
        }
    }

    override suspend fun signOutGoogle(): LoginResult {
        // TODO: 앱의 로그아웃 정책에 따라 구현
        return LoginResult.Success("Signed out successfully") // 현재는 즉시 성공 처리
    }
}