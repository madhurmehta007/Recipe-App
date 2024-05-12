package com.example.recipiesearchapp.networking

import com.example.recipiesearchapp.models.AllRecipeList
import com.example.recipiesearchapp.models.RecipeData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("recipes/random")
    suspend fun getPopularRecipes(@Query("apiKey") apiKey:String, @Query("number") number:String):Response<RecipeData>

    @GET("recipes/complexSearch")
    suspend fun getAllRecipes(@Query("apiKey") apiKey:String):Response<AllRecipeList>
}