package com.woojin.recipick.data.local.datasource

import android.content.Context
import android.content.SharedPreferences
import com.woojin.recipick.util.security.AESEncrypt
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPreferencesDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
): AppPreferencesDataSource {

    private companion object {
        private const val PREFS_FILE_NAME = "recipick.prefs"
        private const val GOOGLE_AUTH_TOKEN = "c057fdc1-ab10-4929-aff0-3c09ee14c4f2"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }

    /** 토큰을 암호화 후 저장 */
    override suspend fun saveAuthToken(token: String) {
        val encryptedToken = AESEncrypt.encrypt(token)
        if (encryptedToken != null) {
            sharedPreferences.edit().putString(GOOGLE_AUTH_TOKEN, encryptedToken).apply()
        }
    }

    /** 암호화 된 토큰을 가져와 복호화 후 리턴 */
    override suspend fun getAuthToken(): String? {
        val encryptedToken = sharedPreferences.getString(GOOGLE_AUTH_TOKEN, null)
        return if (encryptedToken != null) {
            AESEncrypt.decrypt(encryptedToken)
        } else {
            null
        }
    }

    /** 저장된 토큰 값 제거 */
    override suspend fun clearAuthToken() {
        sharedPreferences.edit().putString(GOOGLE_AUTH_TOKEN, null).apply()
    }
}