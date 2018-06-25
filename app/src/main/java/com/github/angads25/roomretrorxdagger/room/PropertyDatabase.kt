package com.github.angads25.roomretrorxdagger.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.github.angads25.roomretrorxdagger.retrofit.model.PropertyListing
import com.github.angads25.roomretrorxdagger.room.repository.PropertyDbRepository

@Database(entities = [(PropertyListing::class)], version = PropertyDatabase.DB_VERSION, exportSchema = false)
abstract class PropertyDatabase: RoomDatabase() {

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "propertyDb"
    }

    abstract fun getPropertyRepository() : PropertyDbRepository
}