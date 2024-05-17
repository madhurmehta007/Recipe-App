package com.example.recipiesearchapp.models

data class AllRecipeList(
    val number: Int,
    val offset: Int,
    val results: List<RecipeDataBrief>,
    val totalResults: Int
)