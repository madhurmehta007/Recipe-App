package com.example.recipiesearchapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipeData")
data class SavedRecipeData(
   @PrimaryKey val id: Int,
    val image: String,
    val readyInMinutes: Int,
    val instructions: String,
    val servings: Int,
    val summary: String,
    val title: String,
    val equipment: ArrayList<Equipment>,
    val ingredients: ArrayList<Ingredient>,
    val pricePerServing: Double,

)
