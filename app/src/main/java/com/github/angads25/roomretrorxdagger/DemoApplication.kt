package com.github.angads25.roomretrorxdagger

import android.app.Activity
import android.app.Application
import com.github.angads25.roomretrorxdagger.dagger.components.ApplicationComponent
import com.github.angads25.roomretrorxdagger.dagger.components.DaggerApplicationComponent
import com.github.angads25.roomretrorxdagger.dagger.modules.AppContextModule
import com.github.angads25.roomretrorxdagger.dagger.modules.DatabaseModule
import com.github.angads25.roomretrorxdagger.dagger.modules.NetworkModule
import java.io.File

class DemoApplication : Application() {
    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        val cacheFile = File(cacheDir, "response_cache")
        applicationComponent = DaggerApplicationComponent.builder()
                .appContextModule(AppContextModule(this@DemoApplication))
                .networkModule(NetworkModule(cacheFile))
                .databaseModule(DatabaseModule())
                .build()
        applicationComponent.injectApplication(this@DemoApplication)
    }

    companion object {
        fun get(activity: Activity) : ApplicationComponent {
            return (activity.application as DemoApplication).applicationComponent
        }
    }
}