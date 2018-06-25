package com.github.angads25.roomretrorxdagger.dagger.components

import android.content.Context

import com.github.angads25.roomretrorxdagger.PropertyApplication

import com.github.angads25.roomretrorxdagger.dagger.modules.AppContextModule
import com.github.angads25.roomretrorxdagger.dagger.modules.DatabaseModule
import com.github.angads25.roomretrorxdagger.dagger.modules.NetworkModule
import com.github.angads25.roomretrorxdagger.dagger.qualifier.ApplicationContext
import com.github.angads25.roomretrorxdagger.dagger.scope.ApplicationScope

import com.github.angads25.roomretrorxdagger.retrofit.repository.PropertyApiRepository
import com.github.angads25.roomretrorxdagger.room.repository.PropertyDbRepository
import dagger.Component

@ApplicationScope
@Component(modules = [(AppContextModule::class) ,(NetworkModule::class), (DatabaseModule::class)])
interface ApplicationComponent {
    fun getPropertyRepository() : PropertyApiRepository

    fun getPropertyDbRepository() : PropertyDbRepository

    @ApplicationContext
    fun getAppContext() : Context

    fun injectApplication(propertyApplication : PropertyApplication)
}