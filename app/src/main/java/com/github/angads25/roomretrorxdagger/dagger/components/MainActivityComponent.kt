package com.github.angads25.roomretrorxdagger.dagger.components

import com.github.angads25.roomretrorxdagger.MainActivity
import com.github.angads25.roomretrorxdagger.dagger.modules.MainActivityModule
import com.github.angads25.roomretrorxdagger.dagger.scope.ActivityScope
import dagger.Component

@ActivityScope
@Component(modules = [(MainActivityModule::class)], dependencies = [(ApplicationComponent::class)])
interface MainActivityComponent {

    fun injectActivity(mainActivity: MainActivity)
}