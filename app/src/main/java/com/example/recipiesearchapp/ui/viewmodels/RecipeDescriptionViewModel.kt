package com.example.recipiesearchapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipiesearchapp.database.RecipeDatabaseRepository
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.example.recipiesearchapp.models.RecipeInformation
import com.example.recipiesearchapp.models.SavedRecipeData
import com.example.recipiesearchapp.networking.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RecipeDescriptionViewModel@Inject constructor(private val repository: Repository,
    private val databaseRepository: RecipeDatabaseRepository) : ViewModel() {

    private val _recipeInformationResponse = MutableLiveData<Response<RecipeInformation>>()
    val allrecipeLists: LiveData<MutableList<SavedRecipeData>> = databaseRepository.allRecipeList
    val recipeInformationResponse: LiveData<Response<RecipeInformation>>
        get() = _recipeInformationResponse


    fun getRecipeInformation(recipeID:String,apiKey:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getRecipeInformation(recipeID,apiKey)
                _recipeInformationResponse.postValue(response)
            } catch (e: IOException) {
                Log.e("error","${e.message}")
            }
        }
    }

    fun insertRecipe(recipe:SavedRecipeData){
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.insertRecipe(recipe)
        }
    }

    fun deleteRecipe(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.deleteRecipe(id)
        }
    }

    fun updateRecipe(recipe:RecipeDataBrief){
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.updateRecipe(recipe)
        }
    }
}