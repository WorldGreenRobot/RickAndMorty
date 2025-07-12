package com.green.robot.rickandmorty.di

import com.green.robot.rickandmorty.utils.android.NetworkState
import org.koin.dsl.module

val utilsModule = module {
    factory {
        NetworkState.getInstance(get())
    }
}