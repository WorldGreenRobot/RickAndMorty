package com.green.robot.rickandmorty.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
interface AppModule {
    @AppScope
    @Binds
    fun context(appInstance: Application): Context
}