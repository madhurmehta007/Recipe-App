package com.example.recipiesearchapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.example.recipiesearchapp.models.SavedRecipeData
import com.example.recipiesearchapp.utils.Converter

@Database(entities = [SavedRecipeData::class,RecipeDataBrief::class], version = 1)
@TypeConverters(Converter::class)
abstract class RecipeDatabase:RoomDatabase() {

    abstract fun recipeDao() : RecipeDao
}