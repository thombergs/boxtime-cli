package io.boxtime.cli.ports.tasklogger

import java.time.Duration

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

    /**
     * Get today's log entries, optionally filtered by a task.
     */
    fun getLogEntriesFromToday(taskId: String? = null): List<LogEntry>

    fun getCurrentLogEntry(): LogEntry?

    fun getCurrentTaskId(): String?

    fun reset()

}

