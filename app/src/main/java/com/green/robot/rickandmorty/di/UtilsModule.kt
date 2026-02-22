package com.green.robot.rickandmorty.di

import android.content.Context
import com.green.robot.rickandmorty.utils.android.NetworkState
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {
    @Provides
    fun provideNetworkState(context: Context) =
        NetworkState.getInstance(context)
}