package com.woojin.recipick.presentation.home.add_recipe.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woojin.recipick.data.local.entity.RecipeEntity
import com.woojin.recipick.domain.usecase.DeleteIngredientUseCase
import com.woojin.recipick.domain.usecase.DeleteStepUseCase
import com.woojin.recipick.domain.usecase.GetRecipeUseCase
import com.woojin.recipick.domain.usecase.UpdateRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    private val updateRecipeUseCase: UpdateRecipeUseCase,
    private val deleteIngredientUseCase: DeleteIngredientUseCase,
    private val deleteStepUseCase: DeleteStepUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetailUiState())
    val uiState: StateFlow<RecipeDetailUiState> = _uiState.asStateFlow()

    /** 레시피 상세 */
    fun recipeDetail(recipeId: Int?) {
        viewModelScope.launch {
            recipeId?.let { id ->
                val data = getRecipeUseCase(id)
                _uiState.update {
                    it.copy(
                        recipeEntity = data,
                        editTitle = data.title,
                        editIngredients = data.ingredients,
                        editSteps = data.steps
                    )
                }
            }
        }
    }

    /** 수정된 레시피 저장 */
    fun updateRecipe(data: RecipeEntity) {
        viewModelScope.launch {
            updateRecipeUseCase(data)
            _uiState.update {
                it.copy(
                    recipeEntity = data,
                    editTitle = data.title,
                    editIngredients = data.ingredients,
                    editSteps = data.steps
                )
            }
        }
    }

    /** 레시피 수정 중 재료 삭제 */
    fun deleteIngredient(index: Int) {
        viewModelScope.launch {
            val afterDeleteData = deleteIngredientUseCase(
                index = index,
                data = _uiState.value.recipeEntity
            )
            _uiState.update {
                it.copy(
                    recipeEntity = afterDeleteData,
                    editIngredients = afterDeleteData.ingredients
                )
            }
        }
    }

    /** 레시피 수정 중 조리 과정 삭제 */
    fun deleteStep(index: Int) {
        viewModelScope.launch {
            val afterDeleteData = deleteStepUseCase(
                index = index,
                data = _uiState.value.recipeEntity
            )
            _uiState.update {
                it.copy(
                    recipeEntity = afterDeleteData,
                    editSteps = afterDeleteData.steps
                )
            }
        }
    }

    /** TopAppBar 뒤로 가기 클릭 시 editMode false 설정 */
    fun updateEditMode(mode: Boolean) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isEditMode = mode
                )
            }
        }
    }

    /** 레시피 타이틀 수정 update */
    fun updateEditTitle(updateTitle: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    editTitle = updateTitle
                )
            }
        }
    }

    /** 레시피 재료 수정 update */
    fun updateEditIngredients(updateIngredients: List<String>) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    editIngredients = updateIngredients
                )
            }
        }
    }

    /** 레시피 과정 수정 update */
    fun updateEditSteps(updateSteps: List<String>) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    editSteps = updateSteps
                )
            }
        }
    }

    /** 재료 삭제 확인 Dialog 관련 값 update */
    fun updateDeleteIngredientDialog(
        pair: Pair<Boolean, Int>
    ) {
        val deleteDialogValue = pair.first
        val deleteIndex = pair.second
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    showDeleteIngredientDialog = deleteDialogValue,
                    deleteIngredientIndex = deleteIndex
                )
            }
        }
    }

    /** 조리 과정 삭제 확인 Dialog 관련 값 update */
    fun updateDeleteStepDialog(
        pair: Pair<Boolean, Int>
    ) {
        val deleteDialogValue = pair.first
        val deleteIndex = pair.second
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    showDeleteStepDialog = deleteDialogValue,
                    deleteStepIndex = deleteIndex
                )
            }
        }
    }
}