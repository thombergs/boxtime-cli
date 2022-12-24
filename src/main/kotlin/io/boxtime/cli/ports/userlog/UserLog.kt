package io.boxtime.cli.ports.userlog

import io.boxtime.cli.ports.taskdatabase.Task

interface UserLog {

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

}