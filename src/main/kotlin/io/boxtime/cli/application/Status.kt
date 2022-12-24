package io.boxtime.cli.application

import io.boxtime.cli.ports.taskdatabase.Task
import java.time.Duration

data class Status(
    val currentTask: Task?,
    val currentTaskDuration: Duration?,
    val currentTaskDurationToday: Duration?,
    val totalDurationToday: Duration?
){

}