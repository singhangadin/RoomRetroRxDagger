package com.github.angads25.roomretrorxdagger.dagger.components

import android.content.Context
import com.github.angads25.roomretrorxdagger.MainActivity
import com.github.angads25.roomretrorxdagger.dagger.modules.ActivityModule
import com.github.angads25.roomretrorxdagger.dagger.modules.MainActivityModule
import com.github.angads25.roomretrorxdagger.dagger.qualifier.ActivityContext
import com.github.angads25.roomretrorxdagger.dagger.scope.ActivityScope
import dagger.Component

@ActivityScope
@Component(modules = [(MainActivityModule::class), (ActivityModule::class)], dependencies = [(ApplicationComponent::class)])
interface MainActivityComponent {

    @ActivityContext fun getContext () : Context

    fun inject(mainActivity: MainActivity)
}