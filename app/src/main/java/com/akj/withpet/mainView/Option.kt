package com.akj.withpet.mainView

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.akj.withpet.R
import com.akj.withpet.roomDB.myDatabase

@Composable
fun OptionView() {
    val placeClicked = remember { mutableStateOf(false) }
    val petClicked = remember { mutableStateOf(false) }

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
        Column {
            Box{
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.TopEnd).clickable{placeClicked.value = false}
                )
                PlaceLike()
            }
        }
    }
    else {
        Column {
            Box{
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.TopEnd).clickable{placeClicked.value = false}
                )
                PetLike()
            }
        }
    }
}

@Composable
fun PlaceLike(){

}

@Composable
fun PetLike(){
    test()
}


@Composable
fun test(){
    val context = LocalContext.current
    val myDB = myDatabase.getInstance(context)!!

    val petList = myDB.myDAO().getPet()
    LazyColumn{
        itemsIndexed(
            items = petList
        ){_, pet ->
            CardView(docItem = pet.animal)
        }
    }
}