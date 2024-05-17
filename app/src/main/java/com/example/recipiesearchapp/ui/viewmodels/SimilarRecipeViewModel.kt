package com.example.recipiesearchapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipiesearchapp.models.SimilarRecipe
import com.example.recipiesearchapp.models.SimilarRecipeItem
import com.example.recipiesearchapp.networking.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SimilarRecipeViewModel@Inject constructor(private val repository: Repository) : ViewModel() {

    private val _similarRecipeDataResponse = MutableLiveData<Response<ArrayList<SimilarRecipeItem>>>()

    val similarRecipeDataResponse: LiveData<Response<ArrayList<SimilarRecipeItem>>>
        get() = _similarRecipeDataResponse

    fun getSimilarRecipeData(id:String,apiKey:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getSimilarRecipes(id,apiKey)
                _similarRecipeDataResponse.postValue(response)
            } catch (e: IOException) {
                Log.e("error","${e.message}")
            }
        }
    }
}