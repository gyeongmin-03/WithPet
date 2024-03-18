package com.akj.withpet.apiService

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object MyViewModel : ViewModel() {
    private val petList = mutableStateOf<List<AnimalApiOutput>?>(null)
    private val placeList = mutableStateOf<List<PlaceApiOutput>?>(null)


    fun setPetApiData(){
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    ApiService.getAnimal()
                }
                petList.value = result
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }

    fun setPlaceApiData(new : List<PlaceApiOutput>){
        placeList.value = new
    }

    fun getPetApiData() : MutableState<List<AnimalApiOutput>?> {
        return petList
    }

    fun getPlaceApiData() : MutableState<List<PlaceApiOutput>?>{
        return placeList
    }


}