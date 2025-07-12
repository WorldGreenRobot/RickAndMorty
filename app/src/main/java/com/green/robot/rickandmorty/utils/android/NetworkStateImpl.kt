package com.green.robot.rickandmorty.utils.android

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkStateImpl(context: Context) : NetworkState {

    private val networkManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isAccessNetwork(): Boolean {
        val info = networkManager.allNetworkInfo
        for(i in 0 until info.size) {
            if(info[i].state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }

        return false
    }
}