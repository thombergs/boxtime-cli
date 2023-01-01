package io.boxtime.cli.ports.tasklogger

import io.boxtime.cli.application.toReadableString
import io.boxtime.cli.ports.taskdatabase.Unit
import java.time.Duration

data class Count(
    val unit: Unit,
    val count: Float
) {
    fun asDuration(): Duration {
        return Duration.ofSeconds(count.toLong())
    }

    override fun toString(): String {
        return if (unit == Unit.SECONDS) {
            Duration.ofSeconds(this.count.toLong()).toReadableString()
        } else {
            "%.2f ${unit.name}".format(count)
        }
    }
}
