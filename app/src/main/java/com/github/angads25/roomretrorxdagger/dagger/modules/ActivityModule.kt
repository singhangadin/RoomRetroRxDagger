package com.github.angads25.roomretrorxdagger.dagger.modules

import android.content.Context
import com.github.angads25.roomretrorxdagger.MainActivity
import com.github.angads25.roomretrorxdagger.dagger.qualifier.ActivityContext
import com.github.angads25.roomretrorxdagger.dagger.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule (private val activity: MainActivity) {

    @Provides
    @ActivityScope
    @ActivityContext
    fun provideActivityContext(): Context {
        return activity
    }

    @Provides
    @ActivityScope
    fun provideActivity(): MainActivity {
        return activity
    }
}