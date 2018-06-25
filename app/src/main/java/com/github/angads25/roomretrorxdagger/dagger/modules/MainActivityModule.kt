package com.github.angads25.roomretrorxdagger.dagger.modules

import com.github.angads25.roomretrorxdagger.architecture.contract.PropertyContract
import com.github.angads25.roomretrorxdagger.dagger.scope.ActivityScope

import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(private val mainView: PropertyContract.PropertyView) {

    @Provides
    @ActivityScope
    fun provideView() : PropertyContract.PropertyView {
        return mainView
    }
}