package com.akj.withpet.mainView

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akj.withpet.EmptyToAll
import com.akj.withpet.R
import com.akj.withpet.REGION_ALL
import com.akj.withpet.addFocusCleaner
import com.akj.withpet.apiService.MyViewModel
import com.akj.withpet.apiService.PlaceApiOutput
import com.akj.withpet.region
import com.akj.withpet.regionName
import com.akj.withpet.roomDB.myDatabase
import com.akj.withpet.roomDB.placeEntity
import com.akj.withpet.ui.theme.Gray
import com.akj.withpet.ui.theme.LightBlue
import com.akj.withpet.ui.theme.SkyBlue
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.timer


@Composable
fun PlaceListView(doc: List<PlaceApiOutput>, recomemnd: Boolean) {
    val placeListClicked = remember { mutableStateOf(false) }
    val rememberListState = rememberLazyListState()
    if (placeListClicked.value) {
        PlaceView(command = { placeListClicked.value = false })
    } else {
        PlaceList(doc, rememberListState, recomemnd) { placeListClicked.value = true }
    }
}


@Composable
fun PlaceView(command: () -> Unit) {
    val item = PlaceClick.placeIndex!!

    val textView = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        if (textView.value) {
            DetailPlace(item = item)
            BackIcon(command = { textView.value = false })
        } else {
            Column {
                Navermap(lat = item.latitude.toDouble(), lon = item.longitude.toDouble())
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
        }
    }


}

@Composable
private fun LikeSwitch(item: PlaceApiOutput) {
    val context = LocalContext.current
    val myDB = myDatabase.getInstance(context)!!
    val like = remember { mutableStateOf(myDB.myDAO().getPlace(item) != null) }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text("즐겨찾기")
        Switch(
            checked = like.value,
            onCheckedChange = {
                like.value = it

                if (it == true) {
                    CoroutineScope(Dispatchers.IO).launch {
                        myDB.myDAO().savePlaceLike(placeEntity(place = item))
                    }
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (myDB.myDAO().getPlace(item) != null) {
                            myDB.myDAO().deletePlaceLike(placeEntity(place = item))
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun TableRow(title: String, content: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Text(
            text = title,
            modifier = Modifier
                .background(color = SkyBlue, shape = RectangleShape)
                .border(
                    border = BorderStroke(color = Color.Black, width = 0.5.dp),
                    shape = RectangleShape
                )
                .width(100.dp)
                .fillMaxHeight()
                .padding(10.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = content,
            modifier = Modifier
                .border(
                    border = BorderStroke(color = Color.Black, width = 0.5.dp),
                    shape = RectangleShape
                )
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(10.dp)
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun DetailPlace(item: PlaceApiOutput) {
    item.apply {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 25.dp)
        ) {
            TableRow("시설명", title)
            TableRow("시설정보", description)
            TableRow("주소", address)
            TableRow("전화번호", tel)
            TableRow("홈페이지", homepage)
            TableRow("휴무일", closedDay)
            TableRow("운영시간", operatingTime)
            TableRow("주차가능 여부", parking)
            TableRow("입장 가능 동물 크기", sizeAble)
            TableRow("제한사항", limit)
            TableRow("실내 가능 여부", insideAble)
            TableRow("실외 가능 여부", outsudeAble)
        }
    }
}


@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun Navermap(lat: Double, lon: Double) {
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoom = 20.0,
                minZoom = 7.0
            )
        )
    }
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                isLocationButtonEnabled = false
            )
        )
    }

    val pos = LatLng(lat, lon)
    val mapCameraPosition = rememberCameraPositionState {
        position = CameraPosition(pos, 13.0)
    }

    NaverMap(
        properties = mapProperties,
        uiSettings = mapUiSettings,
        cameraPositionState = mapCameraPosition,
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Marker(
            state = MarkerState(position = pos),
            iconTintColor = Color.Blue
        )
    }
}

@Composable
fun PlaceList(doc: List<PlaceApiOutput>, rememberListState: LazyListState, recomemnd: Boolean, command: () -> Unit) {
    val dropdownModifier = Modifier
        .width(HalfWidthInDp())
        .heightIn(max = 300.dp)
    var text by remember { mutableStateOf(SearchTextSave.text) }
    var searchText by remember { mutableStateOf(SearchTextSave.text) }

    var isDropDown1 by remember { mutableStateOf(false) }
    var isDropDown2 by remember { mutableStateOf(false) }
    var choiceRegion1 by remember { mutableStateOf(REGION_ALL) }
    var choiceRegion2 by remember { mutableStateOf(REGION_ALL) }

    val focusManager = LocalFocusManager.current

    Column {
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = {
                Text("시설명, 주소 검색 (입력 후 \'완료\' 클릭)")
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                searchText = text
                focusManager.clearFocus()
                SearchTextSave.text = text
            }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 5.dp, end = 5.dp)
                .addFocusCleaner(focusManager),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                textColor = Color.Black
            ),
            shape = RoundedCornerShape(20.dp)
        )

        Row(modifier = Modifier.padding(5.dp)) {

            RegionButton(text = choiceRegion1, Modifier.fillMaxWidth(0.5f)) {
                isDropDown1 = true
                focusManager.clearFocus()
            }

            DropdownMenu(
                expanded = isDropDown1,
                onDismissRequest = { isDropDown1 = false },
                modifier = dropdownModifier
            ) {
                regionName.forEach {
                    DropdownMenuItem(onClick = {
                        choiceRegion1 = it
                        choiceRegion2 = REGION_ALL
                    }) {
                        Text(EmptyToAll(it))
                    }
                }
            }

            RegionButton(text = choiceRegion2, Modifier.fillMaxWidth()) {
                isDropDown2 = true
                focusManager.clearFocus()
            }

            DropdownMenu(
                expanded = isDropDown2,
                onDismissRequest = { isDropDown2 = false },
                modifier = dropdownModifier,
                offset = DpOffset(x = HalfWidthInDp(), y = 0.dp)
            ) {
                region[choiceRegion1]!!.forEach {
                    DropdownMenuItem(onClick = { choiceRegion2 = it }) {
                        Text(EmptyToAll(it))
                    }
                }
            }
        }


        LazyColumn(
            state = rememberListState
        ) {
            itemsIndexed(
                items = doc.filter {
                    (searchText in it.title || searchText in it.address) &&
                            choiceRegion1 in it.address &&
                            choiceRegion2 in it.address
                },
            ) { i, item ->
                if (i % 10 == 0) {
                    RecommandList(recomemnd, command)
                }
                ListBox(item) { command.invoke() }
            }
        }


    }
}


@Composable
private fun RegionButton(text: String, modifier: Modifier, command: () -> Unit) {
    Button(
        onClick = { command.invoke() },
        modifier = modifier.padding(horizontal = 5.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = LightBlue,
            contentColor = Color.White
        ),
        shape = CircleShape
    ) {
        Text(
            text = EmptyToAll(text)
        )
    }
}


@Composable
fun ListBox(item: PlaceApiOutput, recomemnd : Boolean = false, command: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .wrapContentHeight()
            .clickable {
                command.invoke()
                PlaceClick.setIndex(item)
            },
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = if(recomemnd) SkyBlue else Color.White)
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(end = 5.dp)
            ) {
                if(recomemnd){
                    Text(
                        "추천하는 장소예요!!",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Blue,
                        fontSize = 12.sp
                    )
                }
                Text(
                    item.description,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Gray,
                    fontSize = 12.sp
                )
                Text(
                    item.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Text(
                    item.address,
                    fontSize = 15.sp
                )
            }
            Icon(
                painter = painterResource(R.drawable.ic_right),
                contentDescription = null,
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .aspectRatio(1f)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}


private object PlaceClick {
    var placeIndex: PlaceApiOutput? = null

    fun setIndex(new: PlaceApiOutput) {
        placeIndex = new
    }
}

@Composable
fun HalfWidthInDp(): Dp {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val halfScreenWidth = screenWidth / 2
    return halfScreenWidth
}

object SearchTextSave {
    var text = ""
}

@Composable
fun RecommandList(recomemnd: Boolean ,command: () -> Unit) {
    val placeDoc = remember { MyViewModel.getPlaceApiData() }
    RecommandCard(placeDoc.value, recomemnd, command)
}

@Composable
fun RecommandCard(placeDoc: List<PlaceApiOutput>?, recomemnd: Boolean,command: () -> Unit) {
    val place = remember { mutableStateOf(placeDoc!!.random()) }
    var time =0
    Log.d("텟ㅌ", "ㅌㅌ")

    timer(period = 1000L) {
        time++
        Log.d("테스트 타임", time.toString())
        if(time > 9){
            time = 0
            cancel()
            place.value = placeDoc!!.random()
            Log.d("테스트", "테스트")
        }
    }

    ListBox(item = place.value, recomemnd = recomemnd) { command.invoke() }
}