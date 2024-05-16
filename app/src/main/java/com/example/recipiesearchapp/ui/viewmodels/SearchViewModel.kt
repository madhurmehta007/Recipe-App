package com.example.recipiesearchapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipiesearchapp.models.AllRecipeList
import com.example.recipiesearchapp.networking.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel@Inject constructor(private val repository: Repository) : ViewModel() {

    private val _searchDataResponse = MutableLiveData<Response<AllRecipeList>>()

    val searchDataResponse: LiveData<Response<AllRecipeList>>
        get() = _searchDataResponse

    fun getSearchData(apiKey:String,query:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getSearchResult(apiKey,query)
                _searchDataResponse.postValue(response)
            } catch (e: IOException) {
                Log.e("error","${e.message}")
            }
        }
    }
}