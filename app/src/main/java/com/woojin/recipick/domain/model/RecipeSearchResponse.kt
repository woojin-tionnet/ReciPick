package com.woojin.recipick.domain.model

data class RecipeSearchResponse(
    val results: List<RecipeSearchList>
)

data class RecipeSearchList(
    val id: Int? = null,
    val title: String? = null,
    val image: String? = null,
    val imageType: String? = null
)
