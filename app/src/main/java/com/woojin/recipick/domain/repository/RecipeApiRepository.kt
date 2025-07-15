package com.woojin.recipick.domain.repository

import com.woojin.recipick.domain.model.RecipeRandomResponse
import com.woojin.recipick.domain.model.RecipeSearchResponse
import com.woojin.recipick.state.UiState

interface RecipeApiRepository {
    suspend fun getRandomRecipe(): UiState<RecipeRandomResponse>
    suspend fun searchRecipe(
        query: String
    ): UiState<RecipeSearchResponse>
}