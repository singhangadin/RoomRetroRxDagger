package com.github.angads25.roomretrorxdagger.room.repository

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.github.angads25.roomretrorxdagger.architecture.model.PropertyListing
import io.reactivex.Flowable

@Dao
abstract class PropertyDbRepository {

    @Query("select * from propertydata")
    abstract fun getProperties() : Flowable<List<PropertyListing>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(propertyList : List<PropertyListing>)

    @Query("delete from propertydata")
    abstract fun deleteAll()
}