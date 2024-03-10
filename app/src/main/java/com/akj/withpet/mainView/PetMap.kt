package com.akj.withpet.mainView

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.akj.withpet.apiService.MyViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun PetMap(viewModel: MyViewModel) {
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoom = 17.0,
                minZoom = 6.0,
                locationTrackingMode = LocationTrackingMode.Follow,
            )
        )
    }
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(isLocationButtonEnabled = true)
        )
    }

    var markerVisibility by remember { mutableStateOf(true) }
    var cameraPositionState = rememberCameraPositionState().coveringRegion  //이 지역에서 다시 검색을 사용할 때 유용할듯
    Log.d("테스트", cameraPositionState.toString())

    val isMoving =  rememberCameraPositionState().isMoving

    Box(Modifier.fillMaxSize()) {
        NaverMap(
            properties = mapProperties,
            uiSettings = mapUiSettings,
            locationSource = rememberFusedLocationSource(isCompassEnabled = true)
        ){
            if(!isMoving){
                viewModel.getPlaceApiData().value?.forEach {place ->
                    val positionN = place.coordinates.split(", ")[0].substring(startIndex = 1).toDouble()
                    val positionE = place.coordinates.split(", ")[1].substring(startIndex = 1).toDouble()
                    Marker(
                        state = MarkerState(position = LatLng(positionN, positionE))
                    )
                }
            }

        }
    }
}

