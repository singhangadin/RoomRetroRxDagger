package com.github.angads25.roomretrorxdagger.dagger.modules

import android.content.Context

import com.github.angads25.roomretrorxdagger.dagger.qualifier.ApplicationContext
import com.github.angads25.roomretrorxdagger.dagger.scope.ApplicationScope

import dagger.Module
import dagger.Provides

@Module
class AppContextModule(@ApplicationContext private val context: Context) {

    @Provides
    @ApplicationScope
    @ApplicationContext
    fun provideContext() : Context {
        return context
    }
}