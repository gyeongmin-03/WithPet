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
        for(i in allContent){
            placeList += PlaceApiOutput(i)
        }
        MyViewModel.setPlaceApiData(placeList)


        setContent {
            WithPetTheme {
                MainScreenView()
            }
        }
    }
}


@Composable
fun MainScreenView(){
    val navController = rememberNavController()
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
    //        "051-341-3309",

    val item2 = AnimalApiOutput(
        "", "","","","","","","","","","","","","","","","",""
    )

    MaterialTheme{
        PagerCard(item2)
    }
}
