package io.boxtime.cli.ports.tasklogger

import io.boxtime.cli.application.toReadableString
import io.boxtime.cli.ports.taskdatabase.Unit
import java.time.Duration

data class Count(
    val unit: Unit,
    val count: Float
) : Comparable<Count> {
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

    /**
     * Sorts in this order:
     * - time-based counts (sorted by value descending)
     * - other units (sorted by value descending)
     */
    override fun compareTo(other: Count): Int {
        if (this.unit == Unit.SECONDS && other.unit == Unit.SECONDS) {
            return this.count.compareTo(other.count) * -1
        } else if (this.unit == Unit.SECONDS && other.unit != Unit.SECONDS) {
            return -1
        } else if (this.unit != Unit.SECONDS && other.unit == Unit.SECONDS) {
            return 1
        } else if (this.unit != Unit.SECONDS && other.unit != Unit.SECONDS) {
            return this.count.compareTo(other.count) * -1
        }
        return 0
    }
}
