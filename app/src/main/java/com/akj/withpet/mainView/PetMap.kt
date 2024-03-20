package com.akj.withpet.mainView

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akj.withpet.EmptyToAll
import com.akj.withpet.REGION_ALL
import com.akj.withpet.apiService.MyViewModel
import com.akj.withpet.region
import com.akj.withpet.regionName
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
    val dropdownModifier = Modifier
        .width(110.dp)
        .heightIn(max = 300.dp)
    var isDropDown1 by remember { mutableStateOf(false) }
    var isDropDown2 by remember { mutableStateOf(false) }
    var choiceRegion1 by remember { mutableStateOf(REGION_ALL) }
    var choiceRegion2 by remember { mutableStateOf(REGION_ALL) }
    var text by remember {mutableStateOf("")}
    var searchText by remember {mutableStateOf("")}
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(Modifier.fillMaxSize()) {
        Column {
            TextField(
                value = text,
                onValueChange = { text = it },
                placeholder = {
                    Text("검색")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    searchText = text
                }),
                modifier = Modifier.fillMaxSize()

            )

            Row{
                Button(onClick = { isDropDown1 = true }, modifier = Modifier.width(110.dp)) {
                    Text(
                        text = EmptyToAll(choiceRegion1)
                    )
                }

                DropdownMenu(
                    expanded = isDropDown1,
                    onDismissRequest = { isDropDown1 = false },
                    modifier = dropdownModifier
                ) {
                    regionName.forEach {
                        DropdownMenuItem(onClick = {
                            choiceRegion1 = it
                            choiceRegion2 = REGION_ALL
                        }) {
                            Text(EmptyToAll(it))
                        }
                    }
                }

                Button(onClick = { isDropDown2 = true }, modifier = Modifier.width(110.dp)) {
                    Text(
                        text = EmptyToAll(choiceRegion2)
                    )
                }

                DropdownMenu(
                    expanded = isDropDown2,
                    onDismissRequest = { isDropDown2 = false },
                    modifier = dropdownModifier,
                    offset = DpOffset(x = 105.dp, y = 0.dp)
                ) {
                    region[choiceRegion1]!!.forEach {
                        DropdownMenuItem(onClick = { choiceRegion2 = it }) {
                            Text(EmptyToAll(it))
                        }
                    }
                }
            }
        }

        NaverMap(
            properties = mapProperties,
            uiSettings = mapUiSettings,
            locationSource = rememberFusedLocationSource(isCompassEnabled = true)
        ){
            placeList.forEach {place ->
                if(searchText in place.title &&
                    choiceRegion1 in place.address &&
                    choiceRegion2 in place.address)
                {
                    val positionN = place.latitude.toDouble()
                    val positionE = place.longitude.toDouble()

                    Marker(
                        state = MarkerState(position = LatLng(positionN, positionE)),
                        minZoom = 12.0,
                        captionText = place.title,
                        captionMinZoom = 12.0,
                        visible = !(choiceRegion1 == REGION_ALL && searchText == "")
                    )   //마커를 리스트에 저장하고, 가져오는 방식을 고려해야함. 로딩할 때마다 다운로드하고 있음
                }


            }
        }
    }
}

