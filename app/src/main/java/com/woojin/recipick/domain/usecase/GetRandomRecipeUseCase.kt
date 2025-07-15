package com.woojin.recipick.domain.usecase

import com.woojin.recipick.domain.model.RecipeRandomResponse
import com.woojin.recipick.domain.repository.RecipeApiRepository
import com.woojin.recipick.state.UiState
import javax.inject.Inject

class GetRandomRecipeUseCase @Inject constructor(
    private val repository: RecipeApiRepository
) {
    suspend operator fun invoke(): UiState<RecipeRandomResponse> {
        return repository.getRandomRecipe()
    }
}