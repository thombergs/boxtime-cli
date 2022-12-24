package io.boxtime.cli.application

import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.regex.Pattern

val DURATION_PATTERN: Pattern =
    Pattern.compile("^((?<hours>[0-9]+) ?h)? ?((?<minutes>[0-9]+) ?m)? ?((?<seconds>[0-9]+) ?s)?$")


fun parseDuration(string: String): Duration {
    val matcher = DURATION_PATTERN.matcher(string)
    if (!matcher.matches()) {
        throw InvalidDurationStringException(string)
    }
    try {
        val hours = matcher.group("hours")?.toLong() ?: 0
        val minutes = matcher.group("minutes")?.toLong() ?: 0
        val seconds = matcher.group("seconds")?.toLong() ?: 0

        return Duration.of(hours, ChronoUnit.HOURS)
            .plus(Duration.of(minutes, ChronoUnit.MINUTES))
            .plus(Duration.of(seconds, ChronoUnit.SECONDS))
    } catch (e: NumberFormatException) {
        throw InvalidDurationStringException(string, e)
    }

}

internal const val MS = 1L
internal const val SEC = 1000 * MS
internal const val MIN = 60 * SEC
internal const val HOUR = 60 * MIN
internal const val DAY = 24 * HOUR

fun Duration.toReadableString(): String {
    val millis = this.toMillis()
    if (millis == 0L) {
        return "0s"
    }

    val days = millis / DAY
    val remainderDays = millis - days * DAY

    val hours = remainderDays / HOUR
    val remainderHours = remainderDays - hours * HOUR

    val minutes = remainderHours / MIN
    val remainderMinutes = remainderHours - minutes * MIN

    val seconds = remainderMinutes / SEC

    val elements = listOf("${days}d", "${hours}h", "${minutes}m", "${seconds}s")
    val start = elements.indexOfFirst { it[0] != '0' }
    val end = elements.indexOfLast { it[0] != '0' }
    return elements.subList(start, end + 1).joinToString(" ")
}

class InvalidDurationStringException(durationString: String?, e: Exception?) :
    RuntimeException("Invalid duration string: $durationString", e) {

    constructor(durationString: String?) : this(durationString, null)
}