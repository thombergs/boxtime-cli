package io.boxtime.cli.ports.tasklogger

import java.time.Duration

interface TaskLogger {

    fun start(taskId: String)

    /**
     * Stops recording the currently tracked task and returns the task's ID.
     */
    fun stop(): String?

    /**
     * Records a task with a duration but without a start and end time.
     */
    fun logDuration(taskId: String, duration: Duration)

    fun getLogEntries(): List<LogEntry>

    /**
     * Get today's log entries, optionally filtered by a task.
     */
    fun getLogEntriesFromToday(taskId: String? = null): List<LogEntry>

    fun getCurrentLogEntry(): LogEntry?

    fun getCurrentTaskId(): String?

    fun reset()

}

