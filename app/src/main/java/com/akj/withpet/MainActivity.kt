package com.akj.withpet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.akj.withpet.apiService.AnimalApiOutput
import com.akj.withpet.apiService.MyViewModel
import com.akj.withpet.apiService.PlaceApiOutput
import com.akj.withpet.mainView.PagerCard
import com.akj.withpet.ui.theme.WithPetTheme
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MyViewModel()
        viewModel.loadPetApiData()
        viewModel.loadPlaceApiData()

        setContent {
            WithPetTheme {
                MainScreenView(viewModel)
            }
        }
    }
}


@Composable
fun MainScreenView(viewModel: MyViewModel){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController) }
    ) {
        Box(Modifier.padding(it)){
            NavigationGraph(navController = navController, viewModel)
        }
    }
}







@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PetCard(viewModel: MyViewModel){
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    //        "051-341-3309",
    val item = PlaceApiOutput(
        "부루부루",
        "반려의료",
        "동물 약국",
        "운영시간 : 월 09:00~18:00, 화~금 09:00~17:30, 토 09:00~13:30 | 휴무일 : 매주 일요일, 법정공휴일 | 주차 불가 | 반려동물 동반가능 | 반려동물 제한사항 : 제한사항 없음",
        "010-2824-1282",
        "(46555) 부산광역시 북구 만덕대로65번길 9",
        ">http://instagram.com/blooming_pharmacy",
        "N33.25145382, E126.510958"
    )

    val item2 = AnimalApiOutput(
        "", "","","","","","","","","","","","","","","","",""
    )

    MaterialTheme{
        PagerCard(item2)
    }
}
