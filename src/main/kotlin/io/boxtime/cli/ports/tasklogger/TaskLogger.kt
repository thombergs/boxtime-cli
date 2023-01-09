package io.boxtime.cli.ports.tasklogger

import java.time.Duration
import java.time.LocalDateTime

interface TaskLogger {

    fun start(taskId: String)

    /**
     * Stops recording the currently tracked task and returns the created log entry.
     */
    fun stop(): LogEntry?

    /**
     * Records a task with a duration but without a start and end time.
     */
    fun logCount(taskId: String, count: Count)

    fun getLogEntries(): List<LogEntry>

    fun getLogEntries(from: LocalDateTime, to: LocalDateTime, taskId: String? = null): List<LogEntry>

    fun getCurrentLogEntry(): LogEntry?

    fun getCurrentTaskId(): String?

    fun reset()

}

