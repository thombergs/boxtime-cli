package io.boxtime.cli.application

import java.time.Clock
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.util.regex.Pattern

private val DAY_PATTERN = Pattern.compile("^([0-9]+) ?[dD]")
private val WEEK_PATTERN = Pattern.compile("^([0-9]+) ?[wW]")

/**
 * Transforms a relative future time string like "tomorrow" into the corresponding LocalDate.
 */
fun relativeDate(dateString: String, clock: Clock = Clock.systemDefaultZone()): LocalDate? {
    when (dateString.lowercase()) {
        "tomorrow" -> return LocalDate.now(clock).plusDays(1)
        "mon" -> return LocalDate.now(clock).with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        "tue" -> return LocalDate.now(clock).with(TemporalAdjusters.next(DayOfWeek.TUESDAY))
        "wed" -> return LocalDate.now(clock).with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY))
        "thu" -> return LocalDate.now(clock).with(TemporalAdjusters.next(DayOfWeek.THURSDAY))
        "fri" -> return LocalDate.now(clock).with(TemporalAdjusters.next(DayOfWeek.FRIDAY))
        "sat" -> return LocalDate.now(clock).with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
        "sun" -> return LocalDate.now(clock).with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
        "nextweek" -> return LocalDate.now(clock).with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        "nextmonth" -> return firstWorkdayOfMonth(LocalDate.now(clock).plusMonths(1))
    }

    val dayMatcher = DAY_PATTERN.matcher(dateString)
    if (dayMatcher.matches()) {
        val days = dayMatcher.group(1).toLong()
        return LocalDate.now(clock).plusDays(days)
    }

    val weekMatcher = WEEK_PATTERN.matcher(dateString)
    if (weekMatcher.matches()) {
        val days = weekMatcher.group(1).toLong() * 7
        return LocalDate.now(clock).plusDays(days)
    }

    return null
}

private fun firstWorkdayOfMonth(date: LocalDate): LocalDate {
    val firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth())
    return when (firstDayOfMonth.dayOfWeek) {
        DayOfWeek.SATURDAY -> firstDayOfMonth.plusDays(2)
        DayOfWeek.SUNDAY -> firstDayOfMonth.plusDays(1)
        else -> firstDayOfMonth
    }

}
