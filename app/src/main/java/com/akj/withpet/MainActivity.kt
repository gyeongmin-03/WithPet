package com.akj.withpet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.akj.withpet.apiService.AnimalApiOutput
import com.akj.withpet.apiService.MyViewModel
import com.akj.withpet.apiService.PlaceApiOutput
import com.akj.withpet.mainView.PagerCard
import com.akj.withpet.ui.theme.WithPetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MyViewModel()
        viewModel.loadPetApiData()
        viewModel.loadPlaceApiData1()
        viewModel.loadPlaceApiData2()
        viewModel.loadPlaceApiData3()

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
