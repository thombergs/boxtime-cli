package io.boxtime.cli.application

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import org.assertj.core.api.Assertions.assertThat
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.stream.Stream

class LocalDateUtilTest {


    @ParameterizedTest(name = "{0}")
    @MethodSource("testMatrix")
    fun getsRelativeDateFromString(dateString: String, expectedDate: LocalDate) {
        assertThat(relativeDate(dateString, CLOCK)).isEqualTo(expectedDate)
    }

    companion object {

        private val DATE = LocalDate.of(2023, 3, 28) // tuesday
        val CLOCK = Clock.fixed(Instant.parse("2023-03-28T00:00:00Z"), ZoneId.systemDefault())!!

        @JvmStatic
        fun testMatrix(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("tomorrow", DATE.plusDays(1)),
                Arguments.of("nextweek", LocalDate.of(2023,4,3)), // next monday
                Arguments.of("nextmonth", LocalDate.of(2023,4,3)), // first workday in next month

                Arguments.of("TOMORROw", DATE.plusDays(1)),
                Arguments.of("NEXTWEEk", LocalDate.of(2023,4,3)), // next monday
                Arguments.of("nextmonth", LocalDate.of(2023,4,3)), // first workday in next month

                Arguments.of("2d", DATE.plusDays(2)),
                Arguments.of("3d", DATE.plusDays(3)),
                Arguments.of("4d", DATE.plusDays(4)),
                Arguments.of("5d", DATE.plusDays(5)),
                Arguments.of("1w", DATE.plusDays(7)),
                Arguments.of("2w", DATE.plusDays(14)),
                Arguments.of("3w", DATE.plusDays(21)),

                Arguments.of("2D", DATE.plusDays(2)),
                Arguments.of("3D", DATE.plusDays(3)),
                Arguments.of("4D", DATE.plusDays(4)),
                Arguments.of("5D", DATE.plusDays(5)),
                Arguments.of("1W", DATE.plusDays(7)),
                Arguments.of("2W", DATE.plusDays(14)),
                Arguments.of("3W", DATE.plusDays(21)),

                Arguments.of("wed", DATE.plusDays(1)),
                Arguments.of("thu", DATE.plusDays(2)),
                Arguments.of("fri", DATE.plusDays(3)),
                Arguments.of("sat", DATE.plusDays(4)),
                Arguments.of("sun", DATE.plusDays(5)),
                Arguments.of("mon", DATE.plusDays(6)),
                Arguments.of("tue", DATE.plusDays(7)),

                Arguments.of("WED", DATE.plusDays(1)),
                Arguments.of("ThU", DATE.plusDays(2)),
                Arguments.of("FRI", DATE.plusDays(3)),
                Arguments.of("SAt", DATE.plusDays(4)),
                Arguments.of("SUN", DATE.plusDays(5)),
                Arguments.of("MoN", DATE.plusDays(6)),
                Arguments.of("TUE", DATE.plusDays(7)),
            )
        }
    }

}