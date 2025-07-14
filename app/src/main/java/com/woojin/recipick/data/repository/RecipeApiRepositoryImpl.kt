package com.woojin.recipick.data.repository

import android.util.Log
import com.woojin.recipick.BuildConfig
import com.woojin.recipick.data.remote.datasource.NetworkInterface
import com.woojin.recipick.domain.model.RecipeResponse
import com.woojin.recipick.domain.repository.RecipeApiRepository
import com.woojin.recipick.state.UiState
import javax.inject.Inject

class RecipeApiRepositoryImpl @Inject constructor(
    private val networkInterface: NetworkInterface
): RecipeApiRepository {
    override suspend fun getRandomRecipe(): UiState<RecipeResponse> {
        val params = hashMapOf(
            "apiKey" to BuildConfig.SPOONACULAR_KEY
        )
        val response = networkInterface.getRandomRecipe(params)
        if (response.isSuccessful) {
            val model = response.body()
            if (model != null) {
                Log.d("woojinCheck", "model: $model")
                return UiState.Success(model)
            }
        }
        return UiState.Error("")
    }
}