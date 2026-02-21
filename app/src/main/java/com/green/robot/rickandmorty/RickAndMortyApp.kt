package com.green.robot.rickandmorty

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import leakcanary.LeakCanary

@HiltAndroidApp
class RickAndMortyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        LeakCanary.config = LeakCanary.config.copy(dumpHeapWhenDebugging = true)

    }
}