package com.woojin.recipick.presentation.home.add_recipe.state

data class RecipeInputState(
    val title: String = "", //요리 제목
    val ingredients: List<String> = emptyList(), //요리 재료들
    val steps: List<String> = emptyList() //요리 과정
)
