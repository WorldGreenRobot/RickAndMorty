package com.green.robot.rickandmorty

import android.app.Application
import com.green.robot.rickandmorty.di.appModule
import com.green.robot.rickandmorty.di.appNetworkModule
import com.green.robot.rickandmorty.di.dataBaseModule
import com.green.robot.rickandmorty.di.domainModule
import com.green.robot.rickandmorty.di.presenterModule
import com.green.robot.rickandmorty.di.repositoryModule
import com.green.robot.rickandmorty.di.serviceModule
import com.green.robot.rickandmorty.di.utilsModule
import leakcanary.LeakCanary
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RickAndMortyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        LeakCanary.config = LeakCanary.config.copy(dumpHeapWhenDebugging = true)

        startKoin {
            androidLogger()
            androidContext(this@RickAndMortyApp)
            modules(
                appModule,
                appNetworkModule,
                dataBaseModule,
                serviceModule,
                repositoryModule,
                utilsModule,
                domainModule,
                presenterModule
            )
        }
    }
}