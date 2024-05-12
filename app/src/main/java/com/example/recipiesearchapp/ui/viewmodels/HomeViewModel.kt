package com.example.recipiesearchapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipiesearchapp.models.AllRecipeList
import com.example.recipiesearchapp.models.RecipeData
import com.example.recipiesearchapp.networking.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(private val repository: Repository) : ViewModel() {

    private val _popularRecipeDataResponse = MutableLiveData<Response<RecipeData>>()

    val popularRecipeDataResponse:LiveData<Response<RecipeData>>
        get() = _popularRecipeDataResponse

    private val _allRecipeDataResponse = MutableLiveData<Response<AllRecipeList>>()

    val allRecipeDataResponse:LiveData<Response<AllRecipeList>>
        get() = _allRecipeDataResponse

    fun getPopularRecipeData(apiKey:String, number:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getPopularRecipes(apiKey,number)
                _popularRecipeDataResponse.postValue(response)
            } catch (e: IOException) {
                Log.e("error","${e.message}")
            }
        }
    }

    fun getAllRecipeData(apiKey:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getAllRecipes(apiKey)
                _allRecipeDataResponse.postValue(response)
            } catch (e: IOException) {
                Log.e("error","${e.message}")
            }
        }
    }
}