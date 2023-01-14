package io.boxtime.cli.application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.stream.Stream
import kotlin.reflect.KClass

class DurationUtilTest {

    @Test
    fun readableString(){
        assertThat(Duration.ofHours(1).plus(Duration.ofMinutes(2).plus(Duration.ofSeconds(3)))
            .toReadableString()).isEqualTo("1h 2m 3s")

        assertThat(Duration.ofHours(1)
            .toReadableString()).isEqualTo("1h")

        assertThat(Duration.ofMinutes(1)
            .toReadableString()).isEqualTo("1m")

        assertThat(Duration.ofSeconds(1)
            .toReadableString()).isEqualTo("1s")

    }

    @ParameterizedTest(name = "{arguments}")
    @MethodSource("matrix")
    fun parse(durationString: String, expectedDuration: Duration, expectedException: KClass<Exception>?) {
        try {
            val duration = parseDuration(durationString)
            assertThat(duration).isEqualTo(expectedDuration)
        } catch (e: Exception) {
            assertThat(e.javaClass.kotlin).isEqualTo(expectedException)
        }
    }

    companion object {

        private val noException = null
        private val invalidDurationStringException = InvalidDurationStringException::class
        private val notApplicable = Duration.ZERO

        @JvmStatic
        fun matrix(): Stream<Arguments> {
            return Stream.of(
                // valid patterns
                Arguments.of("1h", Duration.of(1, ChronoUnit.HOURS), noException),
                Arguments.of("60", Duration.of(60, ChronoUnit.SECONDS), noException),
                Arguments.of("2m", Duration.of(2, ChronoUnit.MINUTES), noException),
                Arguments.of("3s", Duration.of(3, ChronoUnit.SECONDS), noException),
                Arguments.of("4h5m", Duration.of(245, ChronoUnit.MINUTES), noException),
                Arguments.of("1h1m1s", Duration.of(3661, ChronoUnit.SECONDS), noException),
                Arguments.of("1h1s", Duration.of(3601, ChronoUnit.SECONDS), noException),
                Arguments.of("1m1s", Duration.of(61, ChronoUnit.SECONDS), noException),

                // with spaces
                Arguments.of("4h 5m", Duration.of(245, ChronoUnit.MINUTES), noException),
                Arguments.of("1h 1m1s", Duration.of(3661, ChronoUnit.SECONDS), noException),
                Arguments.of("1h 1s", Duration.of(3601, ChronoUnit.SECONDS), noException),
                Arguments.of("1m 1s", Duration.of(61, ChronoUnit.SECONDS), noException),

                // invalid patterns
                Arguments.of("asd", notApplicable, invalidDurationStringException),
                Arguments.of("1h foo 2m", notApplicable, invalidDurationStringException),
            )
        }

    }

}