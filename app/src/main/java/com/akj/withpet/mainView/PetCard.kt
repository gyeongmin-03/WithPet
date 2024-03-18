package com.akj.withpet.mainView

import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.akj.withpet.R
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
    val clicked = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 100.dp, horizontal = 20.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
            .clickable { clicked.value = true }

    ) {
        if(clicked.value){
            DetailAnimal(docItem)
        } else {
            Column {
                ImageComponent(docItem.popfile)
                DescriptionComponent(docItem)
            }
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
}




@Composable
fun DescriptionComponent(docItem: AnimalApiOutput){
    Row {
        Column {
            Text(docItem.kindCd)    //품종
            Text(docItem.age)   //나이
        }
        Text(docItem.sexCd)   //성별
    }
}

@Composable
fun DetailAnimal(item: AnimalApiOutput){
    Box(modifier = Modifier.verticalScroll(rememberScrollState())){
        Icon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterEnd)
            )
        Column {
            ImageComponent(imageUrl = item.popfile)
            Text("유기번호 : ${item.desertionNo}")
            Text("접수일 : ${item.happenDt}")
            Text("품종 : ${item.kindCd}")
            Text("출생 : ${item.age}")
            Text("체중 : ${item.weight}")
            Text("성별 : ${item.sexCd}")
            Text("중성화 여부 : ${item.neuterYn}")
            Text("특징 : ${item.specialMark}")
            Text("보호소 이름 : ${item.careNm}")
            Text("보호소 전화번호 : ${item.careTel}")
            Text("보호소 주소 : ${item.careAddr}")
            Text("관할기관 : ${item.orgNm}")
            Text("담당자 : ${item.chargeNm}")
            Text("담당자 연락처: ${item.officetel}")
        }
    }
}