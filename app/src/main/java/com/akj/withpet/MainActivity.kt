package com.akj.withpet

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.akj.withpet.apiService.MyViewModel
import com.akj.withpet.apiService.PlaceApiOutput
import com.akj.withpet.mainView.TableRow
import com.akj.withpet.ui.theme.WithPetTheme
import com.opencsv.CSVReader
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MyViewModel.setPetApiData()

        val assetManager = this.assets
        val inputStream = assetManager.open("placeCsvUTF.CSV")
        val reader = CSVReader(InputStreamReader(inputStream))
        val allContent = reader.readAll()
        val placeList = mutableListOf<PlaceApiOutput>()

        allContent.forEach{
            try {
                placeList.add(PlaceApiOutput(it))
            }catch (e: Exception){
                Log.d("에러", e.message.toString())
            }
        }

        MyViewModel.setPlaceApiData(placeList)

        setContent {
            WithPetTheme {
                Box{
                    MainScreenView()
                    GlobalLoadingScreen()
                }
            }
        }
    }
}


@Composable
fun MainScreenView(){
    val navController = rememberNavController()
    BackPressExit() // 뒤로가기 두 번 클릭 -> 앱 종료
    Scaffold(
        bottomBar = { BottomNavigation(navController) }
    ) {
        Box(Modifier.padding(it)){
            NavigationGraph(navController = navController)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TableRow("test", "asssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss")
}
