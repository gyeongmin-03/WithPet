package com.akj.withpet.mainView

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.akj.withpet.R
import com.akj.withpet.roomDB.myDatabase

@Composable
fun OptionView() {
    val placeClicked = remember { mutableStateOf(false) }
    val petClicked = remember { mutableStateOf(false) }
    val myDB = myDatabase.getInstance(LocalContext.current)!!

    if(!placeClicked.value && !petClicked.value){
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .aspectRatio(1f)
                        .padding(10.dp)
                        .border(BorderStroke(width = 2.dp, color = Color.Black), shape = RoundedCornerShape(20.dp))
                        .clickable { placeClicked.value = true }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_city),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.clip(RoundedCornerShape(20.dp))
                    )
                    Text("장소 즐겨찾기",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 15.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .aspectRatio(0.97f)
                        .padding(10.dp)
                        .border(BorderStroke(width = 2.dp, color = Color.Black), shape = RoundedCornerShape(20.dp))
                        .clickable { petClicked.value = true }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_dog),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.clip(RoundedCornerShape(20.dp))
                    )
                    Text("유기동물 즐겨찾기",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 15.dp)
                    )
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