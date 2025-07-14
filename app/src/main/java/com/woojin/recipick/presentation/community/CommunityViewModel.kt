package com.woojin.recipick.presentation.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woojin.recipick.domain.model.RecipeResponse
import com.woojin.recipick.domain.usecase.GetRandomRecipeUseCase
import com.woojin.recipick.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val getRandomRecipeUseCase: GetRandomRecipeUseCase
) : ViewModel() {

    private val _navigateToScreen = MutableSharedFlow<Screen>()
    val navigateToScreen: SharedFlow<Screen> = _navigateToScreen.asSharedFlow()
    private val _randomRecipeState = MutableStateFlow<UiState<RecipeResponse>>(UiState.Uninitialized)
    val randomRecipeState: StateFlow<UiState<RecipeResponse>> = _randomRecipeState.asStateFlow()

    /** 화면 전환 */
    fun navUpdate(value: Screen) {
        viewModelScope.launch {
            _navigateToScreen.emit(value)
        }
    }

    /** 랜덤 레시피 호출 */
    fun requestRandomRecipe() {
        _randomRecipeState.value = UiState.Loading
        viewModelScope.launch {
            _randomRecipeState.emit(getRandomRecipeUseCase())
        }
    }
}