package com.example.recipiesearchapp.networking

import com.example.recipiesearchapp.models.AllRecipeList
import com.example.recipiesearchapp.models.RecipeData
import com.example.recipiesearchapp.models.RecipeInformation
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getPopularRecipes(apiKey: String,number:String): Response<RecipeData> {
        return apiInterface.getPopularRecipes(apiKey,number)
    }

    suspend fun getAllRecipes(apiKey: String): Response<AllRecipeList> {
        return apiInterface.getAllRecipes(apiKey)
    }

    suspend fun getRecipeInformation(recipeID:String,apiKey: String):Response<RecipeInformation>{
        return apiInterface.getRecipeInformation(recipeID,apiKey)
    }

}