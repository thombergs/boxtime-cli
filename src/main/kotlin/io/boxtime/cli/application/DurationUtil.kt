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

internal const val MILLIS_PER_SECOND = 1000
internal const val MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND
internal const val MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE

fun Duration.toReadableString(): String {
    val millis = this.toMillis()
    if (millis == 0L) {
        return "0s"
    }

    val hours = millis / MILLIS_PER_HOUR
    val remainderHours = millis - hours * MILLIS_PER_HOUR

    val minutes = remainderHours / MILLIS_PER_MINUTE
    val remainderMinutes = remainderHours - minutes * MILLIS_PER_MINUTE

    val seconds = remainderMinutes / MILLIS_PER_SECOND

    return ((if (hours > 0) "${hours}h " else "") +
            (if (minutes > 0) "${minutes}m " else "") +
            (if (seconds > 0) "${seconds}s " else "")).trim()
}

class InvalidDurationStringException(durationString: String?, e: Exception?) :
    RuntimeException("Invalid duration string: $durationString", e) {

    constructor(durationString: String?) : this(durationString, null)
}