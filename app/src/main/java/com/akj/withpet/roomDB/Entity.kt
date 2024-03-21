package com.akj.withpet.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akj.withpet.apiService.AnimalApiOutput
import com.akj.withpet.apiService.PlaceApiOutput

@Entity
data class petEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val animal : AnimalApiOutput
)


@Entity
data class placeEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val place : PlaceApiOutput
)