package com.woojin.recipick.data.remote.datasource

import com.woojin.recipick.domain.model.RecipeResponse
import com.woojin.recipick.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NetworkInterface {

    @GET(Constants.SPOONACULAR_RANDOM)
    suspend fun getRandomRecipe(
        @QueryMap options: Map<String, String>
    ): Response<RecipeResponse>

}