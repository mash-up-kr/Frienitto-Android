package com.mashup.frienitto.util

import java.text.SimpleDateFormat
import java.util.*

fun computeExpireTimeDiff(expireTime: String): Long {
    val currentFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val expireDate = currentFormat.parse(expireTime)
    val now = System.currentTimeMillis()
    val nowTime = currentFormat.format(Date(now))
    val nowDate = currentFormat.parse(nowTime)
    val diff = expireDate.time - nowDate.time
    return diff / (60 * 60 * 24 * 1000)
}