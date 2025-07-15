package com.woojin.recipick.presentation.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woojin.recipick.domain.model.RecipeRandomResponse
import com.woojin.recipick.domain.model.RecipeSearchResponse
import com.woojin.recipick.domain.usecase.GetRandomRecipeUseCase
import com.woojin.recipick.domain.usecase.SearchRecipeUseCase
import com.woojin.recipick.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val getRandomRecipeUseCase: GetRandomRecipeUseCase,
    private val searchRecipeUseCase: SearchRecipeUseCase
) : ViewModel() {

    private val _navigateToScreen = MutableSharedFlow<Screen>()
    val navigateToScreen: SharedFlow<Screen> = _navigateToScreen.asSharedFlow()
    private val _randomRecipeState =
        MutableSharedFlow<UiState<RecipeRandomResponse>>()
    val randomRecipeState: SharedFlow<UiState<RecipeRandomResponse>> = _randomRecipeState.asSharedFlow()
    private val _searchRecipeState =
        MutableSharedFlow<UiState<RecipeSearchResponse>>()
    val searchRecipeState: SharedFlow<UiState<RecipeSearchResponse>> =
        _searchRecipeState.asSharedFlow()

    /** 화면 전환 */
    fun navUpdate(value: Screen) {
        viewModelScope.launch {
            _navigateToScreen.emit(value)
        }
    }

    /** 랜덤 레시피 호출 */
    fun requestRandomRecipe() {
        viewModelScope.launch {
            _randomRecipeState.emit(getRandomRecipeUseCase())
        }
    }

    /** 레시피 검색 */
    fun searchRecipe(
        query: String, //재료 or 제목
        cuisine: String? = "", //요리 국가/지역 (italian, korean)
        number: Int? = 0, //검색 결과 레시피 수
    ) {
        viewModelScope.launch {
            _searchRecipeState.emit(
                searchRecipeUseCase(
                    query = query
                )
            )
        }
    }
}