package io.boxtime.cli.ports.tasklogger

import java.time.LocalDateTime

data class LogEntry(
    val taskId: String,
    val startTime: LocalDateTime,
    val count: Float?
) {

    /**
     * Returns true if the log entry is closed (i.e. it has an end time), and false if not.
     */
    fun isClosed(): Boolean {
        return this.count != null;
    }

    /**
     * Returns true if the log entry is still open (i.e. it doesn't have an end time), and false if not.
     */
    fun isOpen(): Boolean {
        return this.count == null;
    }

}