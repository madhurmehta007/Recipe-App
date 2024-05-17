package com.example.recipiesearchapp.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.example.recipiesearchapp.models.SavedRecipeData
import javax.inject.Inject

class RecipeDatabaseRepository@Inject constructor(
    private val recipeDatabase: RecipeDatabase,
//    private val savedRecipe:SavedRecipeData
) {
    private val recipeDao = recipeDatabase.recipeDao()

    val allRecipeList: LiveData<MutableList<SavedRecipeData>> = recipeDao.getAllRecipeData()

    @WorkerThread
    fun insertRecipe(recipe:SavedRecipeData){
        recipeDao.insertRecipeData(recipe)
    }

    @WorkerThread
    fun deleteRecipe(id:Int){
        recipeDao.deleteRecipeData(id)
    }

    @WorkerThread
    fun updateRecipe(recipe:RecipeDataBrief) {
        recipeDao.updateNews(recipe)
    }

}