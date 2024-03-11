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

    fun getPetApiData() : MutableState<List<AnimalApiOutput>?> {
        return pet
    }
}