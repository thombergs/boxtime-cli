package io.boxtime.cli.application

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun LocalDateTime.atStartOfDay(): LocalDateTime {
    return this.truncatedTo(ChronoUnit.DAYS)
}

fun LocalDateTime.atEndOfDay(): LocalDateTime {
    return this.atStartOfDay().plusDays(1).minusNanos(1)
}
