package com.example.recipiesearchapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipeDataBrief")
data class RecipeDataBrief(
  @PrimaryKey  val id: Int,
    val image: String,
    val imageType: String,
    val title: String
)