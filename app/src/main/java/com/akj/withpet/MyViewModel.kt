package com.akj.withpet

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel : ViewModel() {
    private val pet = mutableStateOf<List<AnimalApiOutput>?>(null)

    private val place = mutableStateOf<List<PlaceApiOutput>?>(null)

    fun loadPetApiData(){
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    ApiService.getAnimal()
                }
                pet.value = result
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }

    fun loadPlaceApiData(){
        viewModelScope.launch {
            try {
                var result = withContext(Dispatchers.IO){
                    ApiService.getPlace(pageNo = 1)
                }
                if(result != null){
                    result += withContext(Dispatchers.IO){
                        ApiService.getPlace(pageNo = 2)!!
                    }
                }
                place.value = result
            } catch (e : Exception){

            }
        }
    }

    fun getPetApiData() : MutableState<List<AnimalApiOutput>?> {
        return pet
    }

    fun getPlaceApiData() : MutableState<List<PlaceApiOutput>?> {
        return place
    }
}