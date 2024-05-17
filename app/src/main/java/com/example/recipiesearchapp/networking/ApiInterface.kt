package com.example.recipiesearchapp.networking

import com.example.recipiesearchapp.models.AllRecipeList
import com.example.recipiesearchapp.models.RecipeData
import com.example.recipiesearchapp.models.RecipeInformation
import com.example.recipiesearchapp.models.SimilarRecipe
import com.example.recipiesearchapp.models.SimilarRecipeItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("recipes/random")
    suspend fun getPopularRecipes(@Query("apiKey") apiKey:String, @Query("number") number:String):Response<RecipeData>

    @GET("recipes/complexSearch")
    suspend fun getAllRecipes(@Query("apiKey") apiKey:String):Response<AllRecipeList>

    @GET("recipes/{recipeID}/information")
    suspend fun getRecipeInformation(@Path("recipeID") recipeID:String, @Query("apiKey") apiKey: String):Response<RecipeInformation>

    @GET("recipes/complexSearch")
    suspend fun getSearchResult(@Query("apiKey") apiKey:String, @Query("query") query:String):Response<AllRecipeList>

    @GET("recipes/{id}/similar")
    suspend fun getSimilarRecipes(@Path("id") id:String, @Query("apiKey") apiKey: String):Response<ArrayList<SimilarRecipeItem>>
}