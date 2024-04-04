package com.example.petprojweather

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
class City(@PrimaryKey (autoGenerate = true) val id: Int? = null, val cityName: String)

    @Dao
    interface CityDao{
        @Insert
        fun add(city: City)
        @Delete
        fun delete(city: City)
        @Query ("SELECT * FROM city")
        fun getAll():LiveData<List<City>>

    }
    @Database(entities = [City::class], version = 1)

    abstract class CitiesDatabase: RoomDatabase(){
        abstract fun cityDao(): CityDao
    }

