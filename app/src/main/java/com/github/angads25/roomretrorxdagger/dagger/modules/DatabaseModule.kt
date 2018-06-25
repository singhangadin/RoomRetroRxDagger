package com.github.angads25.roomretrorxdagger.dagger.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.github.angads25.roomretrorxdagger.dagger.qualifier.ApplicationContext
import com.github.angads25.roomretrorxdagger.dagger.scope.ApplicationScope
import com.github.angads25.roomretrorxdagger.room.PropertyDatabase
import com.github.angads25.roomretrorxdagger.room.repository.PropertyDbRepository
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    @ApplicationScope
    fun provideAppDatabase(@ApplicationContext context: Context) : PropertyDatabase {
        return Room.databaseBuilder (
                context,
                PropertyDatabase::class.java,
                PropertyDatabase.DB_NAME
        ).build()
    }

    @Provides
    @ApplicationScope
    fun providePropertyRepository(propertyDatabase: PropertyDatabase) : PropertyDbRepository {
        return propertyDatabase.getPropertyRepository()
    }
}