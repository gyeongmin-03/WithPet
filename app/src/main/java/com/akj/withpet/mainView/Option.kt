package com.akj.withpet.mainView

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.akj.withpet.R
import com.akj.withpet.apiService.AnimalApiOutput
import com.akj.withpet.roomDB.myDatabase
import com.akj.withpet.roomDB.petEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun OptionView() {
    val placeClicked = remember { mutableStateOf(false) }
    val petClicked = remember { mutableStateOf(false) }
    val myDB = myDatabase.getInstance(LocalContext.current)!!

    if(!placeClicked.value && !petClicked.value){
        Box(contentAlignment = Alignment.Center) {
            Column {
                Button(onClick = { placeClicked.value = true }) {
                    Text("장소 \n즐겨찾기")
                }
                Button(onClick = { petClicked.value = true }) {
                    Text("유기동물 \n즐겨찾기")
                }
            }
        }
    }
    else if(placeClicked.value){
        Box(modifier = Modifier.fillMaxSize()){
            PlaceListView(myDB.myDAO().getPlaceList().map{it.place})
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable { placeClicked.value = false }
            )
        }
    }
    else {
        Box(modifier = Modifier.fillMaxSize()){
            PetCardView(doc = myDB.myDAO().getPetList().map { it.animal })
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable { petClicked.value = false }
            )
        }
    }
}