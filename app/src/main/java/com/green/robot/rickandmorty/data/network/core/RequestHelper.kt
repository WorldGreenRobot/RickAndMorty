package com.green.robot.rickandmorty.data.network.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.sync.Mutex
import java.util.concurrent.ConcurrentHashMap

class RequestHelper (
    private val scope: CoroutineScope
) {
    private val mutexes = ConcurrentHashMap<String, Mutex>()
}