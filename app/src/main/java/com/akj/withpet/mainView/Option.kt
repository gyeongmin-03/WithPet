package com.akj.withpet.mainView

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.akj.withpet.R
import com.akj.withpet.apiService.AnimalApiOutput
import com.akj.withpet.roomDB.myDatabase
import com.akj.withpet.roomDB.petEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        Box{
            PlaceLike()
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
        val clicked = remember {
            option_PetCardClick.clicked
        }
        if(clicked.value){
            option_DetailAnimal(option_PetCardClick.petIndex!!)
        }else {
            Box{
                PetLike()
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
}

@Composable
fun PlaceLike(){
    PlaceListView()
}

@Composable
fun PetLike(){
    val myDB = myDatabase.getInstance(LocalContext.current)!!

    val likePetList = myDB.myDAO().getPetList()

    Column(modifier = Modifier.verticalScroll(rememberScrollState())){
        for (i in 0 until likePetList.size step 2){
            val nextIndex = i + 1
            Row {
                option_PetCard(docItem = likePetList[i].animal,
                    Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.5f))
                if(nextIndex < likePetList.size){
                    option_PetCard(docItem = likePetList[nextIndex].animal,
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight())
                }
            }
        }
    }
}


@Composable
fun option_PetCard(docItem : AnimalApiOutput, modifier : Modifier = Modifier){
    Card(
        modifier = modifier
            .padding(20.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
            .clickable {
                option_PetCardClick.onClicked()
                option_PetCardClick.setIndex(docItem)
            },
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp
    ) {
        ImageComponent(docItem.popfile)
    }
}


@Composable
fun option_DetailAnimal(item: AnimalApiOutput){
    val context = LocalContext.current
    val myDB = myDatabase.getInstance(context)!!
    val like = remember{ mutableStateOf(myDB.myDAO().getPet(item.desertionNo.toLong()) != null) }

    Box(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxHeight()
        .fillMaxWidth()){
        Column {
            ImageComponent(imageUrl = item.popfile)
            Row{
                Text("즐겨찾기")
                Switch(
                    checked = like.value,
                    onCheckedChange ={
                        like.value = it

                        if(it == true){
                            CoroutineScope(Dispatchers.IO).launch {
                                myDB.myDAO().savePetLike(petEntity(desertionNo = item.desertionNo.toLong() ,animal = item))
                            }
                        }
                        else {
                            CoroutineScope(Dispatchers.IO).launch {
                                if(myDB.myDAO().getPet(item.desertionNo.toLong()) != null){
                                    myDB.myDAO().deletePetLike(petEntity(item.desertionNo.toLong(), item))
                                }
                            }
                        }
                    }
                )
            }
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


        Icon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clickable { option_PetCardClick.offClicked() }
        )
    }
}




private object option_PetCardClick {
    val clicked = mutableStateOf(false)
    var petIndex : AnimalApiOutput? = null

    fun onClicked() {
        clicked.value = true
    }

    fun offClicked() {
        clicked.value = false
    }

    fun setIndex(new: AnimalApiOutput) {
        petIndex = new
    }
}