package com.akj.withpet.mainView

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.akj.withpet.R
import com.akj.withpet.apiService.AnimalApiOutput
import com.akj.withpet.roomDB.myDatabase
import com.akj.withpet.roomDB.petEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun PetCardView(doc : List<AnimalApiOutput>?){
    val petCardClicked = remember {
        mutableStateOf(false)
    }

    if(doc == null){
        Text("Document is null")
    } else {
        if(petCardClicked.value){
            DetailAnimal(command = {petCardClicked.value = false})
        }
        else{
            Column(modifier = Modifier.verticalScroll(rememberScrollState())){
                for (i in 0 until doc!!.size step 2){
                    val nextIndex = i + 1
                    Row {
                        PetCard(docItem = doc!![i],
                            Modifier
                                .fillMaxWidth(0.5f)
                                .fillMaxHeight(0.5f),
                            command = {petCardClicked.value = true}
                            )
                        if(nextIndex < doc.size){
                            PetCard(docItem = doc!![nextIndex],
                                Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                command = {petCardClicked.value = true}
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun PetCard(docItem : AnimalApiOutput, modifier : Modifier = Modifier, command : () -> Unit){
    Card(
        modifier = modifier
            .padding(20.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
            .clickable {
                command.invoke()
                PetCardClick.setIndex(docItem)
            },
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp
    ) {
        ImageComponent(docItem.popfile)
    }
}



@Composable
fun ImageComponent(imageUrl : String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        contentScale = ContentScale.Fit
    )
}



@Composable
fun DetailAnimal(command: () -> Unit){
    val item = PetCardClick.petIndex!!
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

        BackIcon(command)
    }
}

@Composable
fun BackIcon(command: () -> Unit){
    Box{
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .clickable { command.invoke() }
        )
    }
}

private object PetCardClick {
    var petIndex : AnimalApiOutput? = null

    fun setIndex(new: AnimalApiOutput) {
        petIndex = new
    }
}