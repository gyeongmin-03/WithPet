package com.akj.withpet.mainView

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.akj.withpet.EmptyToAll
import com.akj.withpet.REGION_ALL
import com.akj.withpet.apiService.MyViewModel
import com.akj.withpet.apiService.PlaceApiOutput
import com.akj.withpet.region
import com.akj.withpet.regionName


@Composable
fun PetList(viewModel: MyViewModel){
    val dropdownModifier = Modifier.width(110.dp).heightIn(max=300.dp)
    val keyboardController = LocalSoftwareKeyboardController.current
    val doc by remember { viewModel.getPlaceApiData() }
    var text by remember {
        mutableStateOf("")
    }
    var searchText by remember {
        mutableStateOf("")
    }
    var isDropDown1 by remember { mutableStateOf(false) }
    var isDropDown2 by remember { mutableStateOf(false) }
    var choiceRegion1 by remember { mutableStateOf(REGION_ALL) }
    var choiceRegion2 by remember { mutableStateOf(REGION_ALL) }

    if(doc == null){
        Text("Document is null")
    } else {
        Column {
            Row {
                Button(onClick = { isDropDown1 = true }, modifier = Modifier.width(110.dp)) {
                    Text(
                        text = EmptyToAll(choiceRegion1)
                    )
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

                Button(onClick = { isDropDown2 = true }, modifier = Modifier.width(110.dp)) {
                    Text(
                        text = EmptyToAll(choiceRegion2)
                    )
                }

                DropdownMenu(
                    expanded = isDropDown2,
                    onDismissRequest = { isDropDown2 = false },
                    modifier = dropdownModifier,
                    offset = DpOffset(x = 105.dp, y = 0.dp)
                ) {
                    region[choiceRegion1]!!.forEach {
                        DropdownMenuItem(onClick = { choiceRegion2 = it }) {
                            Text(EmptyToAll(it))
                        }
                    }
                }

                TextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = {
                        Text("검색")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        searchText = text
                    }
                    )
                )
            }

            LazyColumn {
                itemsIndexed(
                    items = doc!!.filter {
                        searchText in it.title &&
                                choiceRegion1 in it.address.split(" ")[1] &&
                                choiceRegion2 in it.address.split(" ")[2]
                    },
                ) { _, item: PlaceApiOutput ->
                    ListBox(item)
                }
            }
        }
    }
}



@Composable
fun ListBox(item: PlaceApiOutput){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .height(100.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ){
        Text("""
            ${item.title}
            ${item.category1} : ${item.category2}
            ${item.address}
        """.trimIndent())
    }
}
