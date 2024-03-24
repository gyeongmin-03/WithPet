package com.akj.withpet.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [petEntity::class, placeEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class myDatabase : RoomDatabase() {
    abstract fun myDAO() : DAO

    companion object{
        private var instance : myDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : myDatabase?{
            if(instance == null){
                synchronized(myDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        myDatabase::class.java,
                        "PetAndPlaceLikeDB"
                    ).allowMainThreadQueries().build()
                }
            }

            return instance
        }

        fun distroyInstance(){
            instance = null
        }
    }
}