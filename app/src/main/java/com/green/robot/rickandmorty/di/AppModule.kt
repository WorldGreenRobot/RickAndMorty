package com.green.robot.rickandmorty.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("IO")) { Dispatchers.IO }
    single(named("Default")) { Dispatchers.Default }
    single(named("Main")) { Dispatchers.Main }
}
