package com.woojin.recipick.data.remote.datasource

import com.woojin.recipick.domain.model.RecipeRandomResponse
import com.woojin.recipick.domain.model.RecipeSearchResponse
import com.woojin.recipick.utils.UrlConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NetworkInterface {

    @GET(UrlConstants.SPOONACULAR_RANDOM)
    suspend fun getRandomRecipe(
        @QueryMap options: Map<String, String>
    ): Response<RecipeRandomResponse>

    @GET(UrlConstants.SPOONACULAR_SEARCH)
    suspend fun searchRecipe(
        @QueryMap options: Map<String, String>
    ): Response<RecipeSearchResponse>


}