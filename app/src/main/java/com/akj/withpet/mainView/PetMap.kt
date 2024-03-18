package com.akj.withpet.mainView

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akj.withpet.apiService.MyViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.internal.NaverMapAccessor
import org.apache.commons.lang3.math.NumberUtils.toDouble

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun PetMap() {
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
    val placeList = MyViewModel.getPlaceApiData().value!!

    Box(Modifier.fillMaxSize()) {
        NaverMap(
            properties = mapProperties,
            uiSettings = mapUiSettings,
            locationSource = rememberFusedLocationSource(isCompassEnabled = true)
        ){
            placeList.forEach {place ->
                val positionN = place.latitude.toDouble()
                val positionE = place.longitude.toDouble()
                Marker(
                    state = MarkerState(position = LatLng(positionN, positionE)),
                    minZoom = 12.0,
                    captionText = place.title,
                    captionMinZoom = 12.0
                )   //마커를 리스트에 저장하고, 가져오는 방식을 고려해야함. 로딩할 때마다 다운로드하고 있음
            }
        }
    }
}

