package com.woojin.recipick.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.woojin.recipick.data.local.dao.RecipeDao
import com.woojin.recipick.data.local.entity.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)
@TypeConverters(ListOfStringConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}