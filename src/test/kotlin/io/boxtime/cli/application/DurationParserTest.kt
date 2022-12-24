package io.boxtime.cli.application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.stream.Stream
import kotlin.reflect.KClass

class DurationParserTest {

    private val parser = DurationParser()

    @ParameterizedTest(name = "{arguments}")
    @MethodSource("matrix")
    fun testSuite(durationString: String, expectedDuration: Duration, expectedException: KClass<Exception>?) {
        try {
            val duration = parser.parse(durationString)
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