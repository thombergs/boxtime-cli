package io.boxtime.cli.application

import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.tasklogger.Count
import java.time.Duration

data class Status(
    val currentTask: TaskWithCount?,
    val todaysTasks: List<TaskWithCount>,
    val totalTimeTrackedToday: Duration,
)

data class TaskWithCount(
    val task: Task,
    val count: Count
)