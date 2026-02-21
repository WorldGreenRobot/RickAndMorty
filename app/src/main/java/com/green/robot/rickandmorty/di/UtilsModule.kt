package com.green.robot.rickandmorty.di

import android.content.Context
import com.green.robot.rickandmorty.utils.android.NetworkState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {
    @Provides
    fun provideNetworkState(@ApplicationContext context: Context) =
        NetworkState.getInstance(context)
}