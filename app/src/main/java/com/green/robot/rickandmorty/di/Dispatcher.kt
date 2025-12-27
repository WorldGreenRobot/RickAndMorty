package com.green.robot.rickandmorty.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Dispatcher(val dispatcher: DispatchersType)

enum class DispatchersType {
    IO,
    Default,
    Main
}