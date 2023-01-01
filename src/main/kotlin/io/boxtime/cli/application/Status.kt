package io.boxtime.cli.application

import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.tasklogger.Count
import java.time.Duration

data class Status(
    val currentTask: Task?,
    val currentTaskDuration: Duration?,
    val currentTaskDurationToday: Duration?,
    val totalDurationToday: Duration?,
    val nonTimeBasedTasksToday: List<TaskWithCount>
)

data class TaskWithCount(
    val task: Task,
    val count: Count
)