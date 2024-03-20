package com.akj.withpet.mainView

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun OptionView() {
    Box(contentAlignment = Alignment.Center) {
        Column {
            Button(onClick = { /*TODO*/ }) {
                Text("장소 \n즐겨찾기")
            }
            Button(onClick = { /*TODO*/ }) {
                Text("유기동물 \n즐겨찾기")
            }
        }
    }
}

@Composable
fun PlaceLike(){

}

@Composable
fun PetLike(){

}