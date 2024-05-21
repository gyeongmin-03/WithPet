package com.akj.withpet.mainView

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.akj.withpet.BackPressMove
import com.akj.withpet.LoadingState
import com.akj.withpet.R
import com.akj.withpet.apiService.AnimalApiOutput
import com.akj.withpet.apiService.MyViewModel
import com.akj.withpet.roomDB.myDatabase
import com.akj.withpet.roomDB.petEntity
import com.akj.withpet.ui.theme.LightBlue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import kotlin.concurrent.thread


@Composable
fun PetCardView(doc : List<AnimalApiOutput>?, refresh : Boolean = true){
    val petCardClicked = remember {
        mutableStateOf(false)
    }
    val rememberScrollState = rememberScrollState()

    if(doc == null){
        Column {
            Text("Document is null")
            RefreshButton()
        }
    } else {
        if(petCardClicked.value){
            PetView(command = {petCardClicked.value = false})
        }
        else{
            PetCardList(rememberScrollState, doc, refresh) {
                petCardClicked.value = true
            }
        }
    }
}

@Composable
private fun PetCardList(rememberScrollState : ScrollState, doc : List<AnimalApiOutput>?, refresh: Boolean, command: () -> Unit){
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState)
        .padding(horizontal = 10.dp)){
        for (i in 0 until doc!!.size step 2){
            val nextIndex = i + 1
            Row {
                PetCard(docItem = doc!![i],
                    Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(),
                    command
                )
                if(nextIndex < doc.size){
                    PetCard(docItem = doc!![nextIndex],
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        command
                    )
                }
            }
        }
        if(refresh == true){
            RefreshButton()
        }
    }
}


@Composable
private fun RefreshButton(){
    Button(
        onClick = {
        MyViewModel.setPetApiData()
        thread {
            LoadingState.show()
            sleep(3*1000L)
            LoadingState.hide()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = LightBlue,
            contentColor = Color.White
        )
    ) {
        Text(text = "새로고침 하기")
    }
}

@Composable
private fun PetCard(docItem : AnimalApiOutput, modifier : Modifier = Modifier, command : () -> Unit){
    Card(
        modifier = modifier
            .padding(10.dp)
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
fun ImageComponent(imageUrl : String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp)),
        contentScale = ContentScale.Fit
    )
}



@Composable
fun PetView(command: () -> Unit){
    val item = PetCardClick.petIndex!!

    val textView = remember {
        mutableStateOf(false)
    }

    val fullSizeImg = remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()){
        if(textView.value){
            DetailPet(item)
            BackIcon(command = {textView.value = false})
        }
        else {
            Column{
                ImageComponent(imageUrl = item.popfile, Modifier.clickable { fullSizeImg.value = true })
                LikeSwitch(item)
                Button(
                    onClick = { textView.value = true },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = LightBlue,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(10.dp)
                ) {
                    Text("상세 내용 보기")
                }
            }
            BackIcon(command)

            if(fullSizeImg.value){
                FullSizeImage(imageUrl = item.popfile) { fullSizeImg.value = false }
            }
        }
    }
}

@Composable
private fun FullSizeImage(imageUrl : String, command : () -> Unit){
    Dialog(
        onDismissRequest = {  },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = true,
        )
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clickable { command.invoke() },
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun DetailPet(item : AnimalApiOutput){
    item.apply {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 25.dp)
        ) {
            TableRow("유기번호", desertionNo)
            TableRow("접수일", happenDt)
            TableRow("품종", kindCd)
            TableRow("출생", age)
            TableRow("체중", weight)
            TableRow("성별", sexCd)
            TableRow("중성화 여부", neuterYn)
            TableRow("특징",specialMark)
            TableRow("보호소 이름",careNm)
            TableRow("보호소 전화번호",careTel)
            TableRow("보호소 주소",careAddr)
            TableRow("관할기관",orgNm)
            TableRow("담당자",chargeNm)
            TableRow("담당자 연락처", officetel)
        }
    }
}


@Composable
private fun LikeSwitch(item : AnimalApiOutput){
    val context = LocalContext.current
    val myDB = myDatabase.getInstance(context)!!
    val like = remember{ mutableStateOf(myDB.myDAO().getPet(item.desertionNo.toLong()) != null) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ){
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
}

@Composable
fun BackIcon(command: () -> Unit){
    BackPressMove(command)
    Box{
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .clickable { command.invoke() }
                .padding(10.dp)
        )
    }
}

private object PetCardClick {
    var petIndex : AnimalApiOutput? = null

    fun setIndex(new: AnimalApiOutput) {
        petIndex = new
    }
}