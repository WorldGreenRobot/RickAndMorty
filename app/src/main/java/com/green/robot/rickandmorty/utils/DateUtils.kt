package com.green.robot.rickandmorty.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val DATE_FORMAT_SERVER =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

private val DATE_FORMAT_LOCAL = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

fun String.parseServerDate(): Date? {
    return try {
        DATE_FORMAT_SERVER.parse(this)
    } catch (_: Exception) {
        null
    }
}

fun Date.formatToServerDateTime(): String {
    return DATE_FORMAT_SERVER.format(this)
}

fun Date.formatLocalDateTime(): String {
    return DATE_FORMAT_LOCAL.format(this)
}
