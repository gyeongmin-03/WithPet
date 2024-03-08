package com.akj.withpet.mainView

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.akj.withpet.apiService.AnimalApiOutput

@Composable
fun PagerCard(docItem : AnimalApiOutput){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 100.dp, horizontal = 20.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))

    ) {
        Column {
            ImageComponent(docItem.popfile)
            descriptionComponent(docItem)
        }
    }
}


@Composable
fun ImageComponent(imageUrl : String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp)),
        contentScale = ContentScale.Crop
    )
//    Image(
//        painter = rememberAsyncImagePainter(imageUrl),
//        contentDescription = null,
//        modifier = Modifier.fillMaxSize()
//    )
}




@Composable
fun descriptionComponent(docItem: AnimalApiOutput){
    Text(docItem.kindCd)    //품종
    Text(docItem.age)   //나이
    Text(docItem.sexCd)   //성별
}