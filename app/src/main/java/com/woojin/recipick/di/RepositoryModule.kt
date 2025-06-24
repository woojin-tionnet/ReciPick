package com.woojin.recipick.di

import com.woojin.recipick.data.impl.LoginRepositoryImpl
import com.woojin.recipick.data.local.impl.RecipeRepositoryImpl
import com.woojin.recipick.domain.repository.LoginRepository
import com.woojin.recipick.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRecipeRepository(
        recipeRepositoryImpl: RecipeRepositoryImpl
    ): RecipeRepository

    @Binds
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository

}