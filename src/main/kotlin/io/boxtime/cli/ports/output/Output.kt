package io.boxtime.cli.ports.output

import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.application.ReportData
import io.boxtime.cli.ports.taskdatabase.Tag
import io.boxtime.cli.ports.tasklogger.Count
import io.boxtime.cli.ports.tasklogger.LogEntry

interface Output {

    fun error(e: Exception)

    fun taskAdded(task: Task)

    fun taskNotFound(taskId: String)

    fun nonUniqueTaskId(taskId: String)

    fun taskStarted(task: Task)

    fun canOnlyTrackTasksWithTimeUnit(task: Task)

    fun taskStopped(task: Task, logEntry: LogEntry)

    fun notCurrentlyTracking()

    fun listTasks(tasks: List<Task>)

    fun listTags(tags: List<Tag>)

    fun tasksReset()

    fun logsReset()

    fun taskLogged(task: Task, count: Count)

    fun report(reportData: ReportData)

}