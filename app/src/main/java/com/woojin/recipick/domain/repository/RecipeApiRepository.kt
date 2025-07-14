package com.woojin.recipick.domain.repository

import com.woojin.recipick.domain.model.RecipeResponse
import com.woojin.recipick.state.UiState

interface RecipeApiRepository {
    suspend fun getRandomRecipe(): UiState<RecipeResponse>
}