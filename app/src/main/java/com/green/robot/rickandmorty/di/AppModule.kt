package com.green.robot.rickandmorty.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val appModule = module {
    factory<CoroutineScope>{ CoroutineScope(SupervisorJob() + Dispatchers.Default) }
}