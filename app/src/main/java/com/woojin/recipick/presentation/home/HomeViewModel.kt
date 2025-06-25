package com.woojin.recipick.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woojin.recipick.data.local.dao.RecipeDao
import com.woojin.recipick.data.local.entity.RecipeEntity
import com.woojin.recipick.presentation.home.add_recipe.state.RecipeInputState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeDao: RecipeDao
) : ViewModel() {
    private val _navigateToScreen = MutableSharedFlow<Screen>()
    val navigateToScreen: SharedFlow<Screen> = _navigateToScreen.asSharedFlow()

    private val _recipeInputState = mutableStateOf(RecipeInputState())

    private val _selectedIngredientName = mutableStateOf<String>("")
    val selectedIngredientName: State<String> = _selectedIngredientName

    /** 화면 전환 */
    fun navUpdate(value: Screen) {
        viewModelScope.launch {
            _navigateToScreen.emit(value)
        }
    }

    /** 현재 까지 앱 DB에 저장된 레시피 collect 할 변수 */
    val recipes: StateFlow<List<RecipeEntity>> = recipeDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(0),
            initialValue = emptyList()
        )

    /** 레시피 제목 update */
    fun updateRecipeTitle(title: String) {
        _recipeInputState.value = _recipeInputState.value.copy(title = title)
    }

    /** 레시피 재료 목록 update */
    fun updateIngredients(ingredients: List<String>) {
        _recipeInputState.value = _recipeInputState.value.copy(ingredients = ingredients)
    }

    /** 조리 단계 update */
    fun updateRecipeSteps(steps: List<String>) {
        _recipeInputState.value = _recipeInputState.value.copy(steps = steps)
        saveRecipe()
    }

    /** 레시피 저장 */
    private fun saveRecipe() {
        val recipeEntity = RecipeEntity(
            id = null,
            title = _recipeInputState.value.title,
            ingredients = _recipeInputState.value.ingredients,
            steps = _recipeInputState.value.steps
        )
        viewModelScope.launch {
            try {
                recipeDao.insert(recipeEntity)
                _recipeInputState.value = RecipeInputState()
                navUpdate(Screen.Main)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /** 레시피 제거 */
    fun deleteRecipe(recipeId: Int?) {
        viewModelScope.launch {
            recipeId?.let { id ->
                recipeDao.delete(id)
            }
        }
    }

    /** 재료 선택 화면 에서 가져 오는 재료의 이름 저장 */
    fun selectedIngredientName(name: String) {
        _selectedIngredientName.value = name
    }

    /** 재료 선택 화면 에서 가져 오는 재료의 이름 저장 이후 초기화 */
    fun clearSelectedIngredientName() {
        _selectedIngredientName.value = ""
    }
}