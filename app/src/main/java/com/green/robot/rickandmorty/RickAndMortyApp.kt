package com.green.robot.rickandmorty

import android.app.Application
import android.content.Context
import com.green.robot.rickandmorty.di.AppComponent
import com.green.robot.rickandmorty.di.DaggerAppComponent
import leakcanary.LeakCanary

class RickAndMortyApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        LeakCanary.config = LeakCanary.config.copy(dumpHeapWhenDebugging = true)
    }
}

fun Context.getAppComponent(): AppComponent {
    return when (this) {
        is RickAndMortyApp -> appComponent
        else -> applicationContext.getAppComponent()
    }
}