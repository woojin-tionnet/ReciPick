package com.woojin.recipick.data.repository

import com.woojin.recipick.data.local.dao.RecipeDao
import com.woojin.recipick.data.local.entity.RecipeEntity
import com.woojin.recipick.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
) : RecipeRepository {
    override suspend fun getRecipe(
        recipeId: Int
    ): RecipeEntity {
        return recipeDao.getRecipe(recipeId)
    }

    override suspend fun updateRecipe(data: RecipeEntity) = recipeDao.updateRecipe(data)
    override suspend fun deleteIngredient(
        index: Int,
        data: RecipeEntity
    ): RecipeEntity {
        val currentIngredients = data.ingredients
        if (index >= 0 && index < currentIngredients.size) {
            val updateIngredients = currentIngredients.toMutableList()
            updateIngredients.removeAt(index)
            val newRecipeData = data.copy(
                ingredients = updateIngredients.toList()
            )
            recipeDao.updateRecipe(newRecipeData)
            return newRecipeData
        }
        return data
    }

    override suspend fun deleteStep(
        index: Int,
        data: RecipeEntity
    ): RecipeEntity {
        val currentSteps = data.steps
        if (index >= 0 && index < currentSteps.size) {
            val updateSteps = currentSteps.toMutableList()
            updateSteps.removeAt(index)
            val newRecipeData = data.copy(
                steps = updateSteps.toList()
            )
            recipeDao.updateRecipe(newRecipeData)
            return newRecipeData
        }
        return data
    }
}