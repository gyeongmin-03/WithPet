package com.akj.withpet

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.akj.withpet.apiService.MyViewModel
import com.akj.withpet.mainView.FavoriteView
import com.akj.withpet.mainView.PetCardView
import com.akj.withpet.mainView.PlaceListView
import com.akj.withpet.ui.theme.LightBlue

const val PetFavorite = "Favorite"
const val PlaceList = "PetList"
const val PetCard = "PetCardView"

sealed class BottomNavItem(
    val title: Int, val icon: Int, val screenRoute: String
){
    object List : BottomNavItem(R.string.text_pet_list, R.drawable.ic_home_work, PlaceList)
    object Favorite : BottomNavItem(R.string.text_pet_favorite, R.drawable.ic_star, PetFavorite)
    object Card : BottomNavItem(R.string.text_pet_card, R.drawable.ic_pet, PetCard)
}


@Composable
fun BottomNavigation(navController: NavHostController){
    val items = listOf<BottomNavItem>(
        BottomNavItem.Card,
        BottomNavItem.Favorite,
        BottomNavItem.List
    )

    androidx.compose.material.BottomNavigation(
        backgroundColor = Color.White,
        modifier = Modifier.border(width = 1.dp, color = Color.Black)
    ) {
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
                selectedContentColor = LightBlue,
                unselectedContentColor = Color.LightGray
            )
        }
    }
}



@Composable
fun NavigationGraph(navController: NavHostController){
    val viewModel = MyViewModel
    var petDoc by remember { viewModel.getPetApiData() }
    var placeDoc by remember { viewModel.getPlaceApiData() }
    NavHost(navController = navController, startDestination = BottomNavItem.Favorite.screenRoute){
        composable(BottomNavItem.Card.screenRoute){
            PetCardView(petDoc)
        }
        composable(BottomNavItem.Favorite.screenRoute){
            FavoriteView()
        }
        composable(BottomNavItem.List.screenRoute){
            PlaceListView(placeDoc!!, recommend = true)
        }
    }
}