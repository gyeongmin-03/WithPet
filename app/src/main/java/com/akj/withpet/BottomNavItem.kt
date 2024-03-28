package com.akj.withpet

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.akj.withpet.mainView.OptionView
import com.akj.withpet.mainView.PetCardView
import com.akj.withpet.mainView.PlaceListView

const val Option = "Option"
const val PetList = "PetList"
const val PetCard = "PetCardView"

sealed class BottomNavItem(
    val title: Int, val icon: Int, val screenRoute: String
){
    object option : BottomNavItem(R.string.text_pet_map, R.drawable.ic_map, Option)
    object List : BottomNavItem(R.string.text_pet_list, R.drawable.ic_list, PetList)
    object Card : BottomNavItem(R.string.text_pet_card, R.drawable.ic_card, PetCard)
}


@Composable
fun BottomNavigation(navController: NavHostController){
    val items = listOf<BottomNavItem>(
        BottomNavItem.option,
        BottomNavItem.Card,
        BottomNavItem.List
    )

    androidx.compose.material.BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach{ item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                    )
                },
                label = { Text(stringResource(id = item.title), fontSize = 9.sp) },
                selected = currentRoute == item.screenRoute,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.screenRoute){
                        navController.graph.startDestinationRoute?.let{
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.Gray
            )
        }
    }
}



@Composable
fun NavigationGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = BottomNavItem.Card.screenRoute){
        composable(BottomNavItem.option.screenRoute){
            OptionView()
        }
        composable(BottomNavItem.Card.screenRoute){
            PetCardView()
        }
        composable(BottomNavItem.List.screenRoute){
            PlaceListView()
        }
    }
}