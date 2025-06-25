package com.woojin.recipick.presentation.home.add_recipe.detail

import com.woojin.recipick.data.local.entity.RecipeEntity

data class RecipeDetailUiState(
    val isEditMode: Boolean = false, // 수정 모드 or 읽기 모드 여부 저장
    val recipeEntity: RecipeEntity = RecipeEntity(null, "", emptyList(), emptyList()), // 레시피 정보 저장
    val editTitle: String = recipeEntity.title, // 수정 모드 레시피 제목 저장
    val editIngredients: List<String> = recipeEntity.ingredients, // 수정 모드 레시피 재료 저장
    val editSteps: List<String> = recipeEntity.steps, // 수정 모드 레시피 조리 과정 저장
    val showDeleteIngredientDialog: Boolean = false, // 재료 삭제 dialog 표시 여부
    val deleteIngredientIndex: Int = -1, // 삭제 재료 인덱스 저장
    val showDeleteStepDialog: Boolean = false, // 조리 과정 삭제 dialog 표시 여부
    val deleteStepIndex: Int = -1, // 조리 과정 삭제 인덱스 저장
)
