package io.boxtime.cli.ports.tasklogger

import java.time.LocalDateTime

data class LogEntry(
    val taskId: String,
    val startTime: LocalDateTime,
    val count: Float?
) {

}