package com.akj.withpet.apiService

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel : ViewModel() {
    private val pet = mutableStateOf<List<AnimalApiOutput>?>(null)

    private val place1 = mutableStateOf<List<PlaceApiOutput>?>(null)
    private val place2 = mutableStateOf<List<PlaceApiOutput>?>(null)
    private val place3 = mutableStateOf<List<PlaceApiOutput>?>(null)

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

    fun loadPlaceApiData1(){
        viewModelScope.launch {
            try {
                var result = withContext(Dispatchers.IO){
                    ApiService.getPlace(pageNo = 1)
                }
                place1.value = result
            } catch (e : Exception){

            }
        }
    }

    fun loadPlaceApiData2(){
        viewModelScope.launch {
            try {
                var result = withContext(Dispatchers.IO){
                    ApiService.getPlace(pageNo = 2)
                }
                place2.value = result
            } catch (e : Exception){

            }
        }
    }

    fun loadPlaceApiData3(){
        viewModelScope.launch {
            try {
                var result = withContext(Dispatchers.IO){
                    ApiService.getPlace(pageNo = 3)
                }
                place3.value = result
            } catch (e : Exception){

            }
        }
    }

    fun getPetApiData() : MutableState<List<AnimalApiOutput>?> {
        return pet
    }

    fun getPlaceApiData1() : MutableState<List<PlaceApiOutput>?> {
        return place1
    }
    fun getPlaceApiData2() : MutableState<List<PlaceApiOutput>?> {
        return place2
    }
    fun getPlaceApiData3() : MutableState<List<PlaceApiOutput>?> {
        return place3
    }
}