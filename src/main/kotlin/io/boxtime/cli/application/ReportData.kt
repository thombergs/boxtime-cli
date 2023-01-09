package io.boxtime.cli.application

import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.tasklogger.Count
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

data class ReportData(
    val from: LocalDateTime,
    val to: LocalDateTime,
    val currentTask: TaskWithCount?,
    val loggedTasks: List<TaskWithCount>,
    val totalTimeLogged: Duration,
)

data class TaskWithCount(
    val task: Task,
    val count: Count
)