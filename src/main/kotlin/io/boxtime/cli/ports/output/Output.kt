package io.boxtime.cli.ports.output

import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.application.Status

interface Output {

    fun error(e: Exception)

    fun taskAdded(task: Task)

    fun taskNotFound(taskId: String)

    fun taskStarted(task: Task)

    fun taskStopped(task: Task)

    fun notCurrentlyTracking()

    fun listTasks(tasks: List<Task>)

    fun tasksReset()

    fun logsReset()

    fun taskLogged(task: Task, durationString: String)

    fun status(status: Status)

}