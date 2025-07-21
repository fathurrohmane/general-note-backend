package com.elkusnandi.generalnote.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

object DateTimeUtil {

    fun getCurrentLocalDate() = LocalDate.now(ZoneId.of("Asia/Jakarta"))

    fun getCurrentLocalTime() = LocalTime.now(ZoneId.of("Asia/Jakarta"))

}