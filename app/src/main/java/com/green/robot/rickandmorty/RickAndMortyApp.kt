package com.green.robot.rickandmorty

import android.app.Application
import com.green.robot.rickandmorty.di.appModule
import com.green.robot.rickandmorty.di.databaseModule
import com.green.robot.rickandmorty.di.domainModule
import com.green.robot.rickandmorty.di.networkModule
import com.green.robot.rickandmorty.di.repositoryModule
import com.green.robot.rickandmorty.di.serviceModule
import com.green.robot.rickandmorty.di.uiModule
import com.green.robot.rickandmorty.di.utilsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RickAndMortyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RickAndMortyApp)
            modules(
                appModule,
                networkModule,
                repositoryModule,
                serviceModule,
                uiModule,
                domainModule,
                utilsModule,
                databaseModule
            )
        }
    }
}