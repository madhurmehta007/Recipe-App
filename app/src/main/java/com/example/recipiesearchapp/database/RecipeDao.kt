package com.example.recipiesearchapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipiesearchapp.models.SavedRecipeData
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipeData")
    fun getAllRecipeData(): LiveData<MutableList<SavedRecipeData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeData(vararg recipeDataList:SavedRecipeData)

//    @Query("DELETE FROM recipeData WHERE id = :id")
//    fun deleteRecipeData(id:Int)

    @Delete
    fun deleteRecipeData(vararg recipeData: SavedRecipeData)
}