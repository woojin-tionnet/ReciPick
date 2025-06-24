package com.woojin.recipick.data.local.datasource

interface AppPreferencesDataSource {
    suspend fun saveAuthToken(token: String)
    suspend fun getAuthToken(): String?
    suspend fun clearAuthToken()
}