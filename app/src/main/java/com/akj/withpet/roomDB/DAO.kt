package com.akj.withpet.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akj.withpet.apiService.PlaceApiOutput

@Dao
interface DAO {
    @Query("SELECT * FROM petEntity")
    fun getPetList() : List<petEntity>

    @Query("SELECT * FROM placeEntity")
    fun getPlaceList() : List<placeEntity>

    @Query("SELECT * FROM petEntity WHERE desertionNo = :desertionNo")
    fun getPet(desertionNo : Long) : petEntity?

    @Query("SELECT * FROM placeEntity WHERE place = :item")
    fun getPlace(item : PlaceApiOutput) : placeEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun savePetLike(pet : petEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun savePlaceLike(place : placeEntity)

    @Delete
    fun deletePetLike(pet : petEntity)

    @Delete
    fun deletePlaceLike(place : placeEntity)
}