package com.green.robot.rickandmorty.utils.android

import android.content.Context

interface NetworkState {

    fun isAccessNetwork(): Boolean

    companion object {
        fun getInstance(context: Context): NetworkState {
            return NetworkStateImpl(context)
        }
    }
}