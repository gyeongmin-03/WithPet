package com.akj.withpet.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DAO {
    @Query("SELECT * FROM petEntity")
    fun getPet() : List<petEntity>

    @Query("SELECT * FROM placeEntity")
    fun getPlace() : List<placeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePetLike(pet : petEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePlaceLike(place : placeEntity)

    @Delete
    fun deletePetLike(pet : petEntity)

    @Delete
    fun deletePlaceLike(place : placeEntity)
}