package com.akj.withpet

const val PetMap = "PetMap"
const val PetList = "PetList"
const val PetCard = "PetCard"

sealed class BottomNavItem(
    val title: Int, val icon: Int, val screenRoute: String
){
    object Map : BottomNavItem(R.string.text_pet_map, R.drawable.ic_map, PetMap)
    object List : BottomNavItem(R.string.text_pet_list, R.drawable.ic_list, PetList)
    object Card : BottomNavItem(R.string.text_pet_card, R.drawable.ic_card, PetCard)
}
