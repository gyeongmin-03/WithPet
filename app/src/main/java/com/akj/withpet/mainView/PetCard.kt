package com.akj.withpet.mainView

import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.akj.withpet.apiService.AnimalApiOutput
import com.akj.withpet.apiService.MyViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PetCard(){
    val viewModel = MyViewModel
    var doc by remember { viewModel.getPetApiData() }
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2,
        initialPageOffsetFraction = 0.0f,
        pageCount = {Int.MAX_VALUE}
    )

    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        decayAnimationSpec = rememberSplineBasedDecay(),
        snapAnimationSpec = tween(durationMillis = 300)
    )

    if(doc == null){
        Text("Document is null")
    } else {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(start = 32.dp, end = 32.dp),
//            pageSize = PageSize.Fixed(pageSize = 100.dp),
            outOfBoundsPageCount = 1,
//            pageSpacing = pageSpacing,
//            flingBehavior = fling,
//            snapPosition = snapPosition
        ) {page : Int ->
            PagerCard(doc!![page % doc!!.size])
        }
    }
}


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