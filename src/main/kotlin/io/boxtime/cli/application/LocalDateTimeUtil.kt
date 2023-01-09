package io.boxtime.cli.application

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

val DATETIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

fun LocalDateTime.atStartOfDay(): LocalDateTime {
    return this.truncatedTo(ChronoUnit.DAYS)
}

fun LocalDateTime.atEndOfDay(): LocalDateTime {
    return this.atStartOfDay().plusDays(1).minusNanos(1)
}

fun LocalDateTime.format(): String {
    return this.format(DATETIME_FORMATTER)
}
