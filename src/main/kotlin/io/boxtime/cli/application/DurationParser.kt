package io.boxtime.cli.application

import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.regex.Pattern

class DurationParser {

    companion object {
        val DURATION_PATTERN =
            Pattern.compile("^((?<hours>[0-9]+) ?h)? ?((?<minutes>[0-9]+) ?m)? ?((?<seconds>[0-9]+) ?s)?$")
    }

    fun parse(string: String): Duration {
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

}

class InvalidDurationStringException(durationString: String?, e: Exception?) :
    RuntimeException("Invalid duration string: $durationString", e) {

    constructor(durationString: String?) : this(durationString, null)
}