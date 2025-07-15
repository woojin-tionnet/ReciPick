package com.woojin.recipick.domain.usecase

import com.woojin.recipick.domain.model.RecipeSearchResponse
import com.woojin.recipick.domain.repository.RecipeApiRepository
import com.woojin.recipick.state.UiState
import javax.inject.Inject

class SearchRecipeUseCase @Inject constructor(
    private val repository: RecipeApiRepository
) {
    suspend operator fun invoke(
        query: String
    ): UiState<RecipeSearchResponse> {
        return repository.searchRecipe(
            query = query
        )
    }
}