package com.akj.withpet.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.akj.withpet.apiService.AnimalApiOutput
import com.akj.withpet.apiService.PlaceApiOutput
import com.google.gson.Gson


@Entity
data class petEntity (
    @PrimaryKey(autoGenerate = true)
    val desertionNo: Long = 0L,
    @TypeConverters(Converters::class)
    val animal: AnimalApiOutput
)

@Entity
data class placeEntity (
    @PrimaryKey()
    @TypeConverters(Converters::class)
    val place: PlaceApiOutput
)

class Converters {
    @TypeConverter
    fun fromAnimalApiOutput(value: AnimalApiOutput): String = Gson().toJson(value)

    @TypeConverter
    fun toAnimalApiOutput(value: String): AnimalApiOutput = Gson().fromJson(value, AnimalApiOutput::class.java)

    @TypeConverter
    fun fromPlaceApiOutput(value: PlaceApiOutput): String = Gson().toJson(value)

    @TypeConverter
    fun toPlaceApiOutput(value: String): PlaceApiOutput = Gson().fromJson(value, PlaceApiOutput::class.java)
}