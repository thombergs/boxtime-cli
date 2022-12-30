package io.boxtime.cli.application

import io.boxtime.cli.ports.output.Output
import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.taskdatabase.TaskDatabase
import io.boxtime.cli.ports.tasklogger.TaskLogger
import java.time.Duration
import java.time.LocalDateTime

/**
 * Central facade for all use cases.
 */
class Application(
    private val taskDatabase: TaskDatabase,
    private val taskLogger: TaskLogger,
    private val output: Output,
) {

    fun addTask(title: String, extractTags: Boolean) {
        try {
            val task = Task(title, extractTags);
            taskDatabase.addTask(task)
            output.taskAdded(task)
        } catch (e: Exception) {
            output.error(e)
        }
    }

    fun startTask(taskId: String) {
        try {
            stopTask(silent = true)
            val task = getTaskStartingWith(taskId) ?: return
            taskLogger.start(task.id)
            output.taskStarted(task)
        } catch (e: Exception) {
            output.error(e)
        }
    }

    fun stopTask(silent: Boolean = false) {
        try {
            val logEntry = taskLogger.stop()
            if (logEntry == null) {
                if (!silent) output.notCurrentlyTracking()
                return
            }
            val task = taskDatabase.findTaskById(logEntry.taskId)
            if (task == null) {
                if(!silent) output.taskNotFound(logEntry.taskId)
                return
            }
            output.taskStopped(task, logEntry)
        } catch (e: Exception) {
            output.error(e)
        }
    }

    fun listTasks(count: Int = 10, filter: String?) {
        try {
            output.listTasks(taskDatabase.listTasks(count, filter))
        } catch (e: Exception) {
            output.error(e)
        }
    }

    fun resetTasks() {
        try {
            taskDatabase.reset()
            output.tasksReset()
        } catch (e: Exception) {
        }
    }

    fun resetLog() {
        try {
            taskLogger.reset()
            output.logsReset()
        } catch (e: Exception) {
            output.error(e)
        }
    }

    fun logTask(taskId: String, durationString: String) {
        try {
            val task = getTaskStartingWith(taskId) ?: return
            val duration = parseDuration(durationString)
            taskLogger.logDuration(taskId, duration)
            output.taskLogged(task, durationString)
        } catch (e: Exception) {
            output.error(e)
        }
    }

    private fun getTaskStartingWith(taskId: String): Task? {
        val tasks = taskDatabase.findTaskByIdStartsWith(taskId)
        if (tasks.size > 1) {
            output.nonUniqueTaskId(taskId)
            return null
        }
        if (tasks.isEmpty()) {
            output.taskNotFound(taskId)
            return null
        }
        return tasks[0]
    }

    fun status() {
        try {

            val currentLogEntry = taskLogger.getCurrentLogEntry()
            val currentTask = currentLogEntry
                ?.let { taskDatabase.findTaskById(it.taskId) }
            val currentTaskDuration = currentLogEntry
                ?.let { it.duration ?: Duration.between(it.startTime, LocalDateTime.now()) }
            val currentTaskDurationToday = currentLogEntry
                ?.let { taskLogger.getLogEntriesFromToday(it.taskId) }
                ?.map { it.duration ?: Duration.between(it.startTime, LocalDateTime.now()) }
                ?.fold(Duration.ZERO) { e1, e2 -> if (e2 == null) e1 else e1.plus(e2) }
            val totalDurationToday = taskLogger.getLogEntriesFromToday()
                .map { it.duration ?: Duration.between(it.startTime, LocalDateTime.now()) }
                .fold(Duration.ZERO) { e1, e2 -> if (e2 == null) e1 else e1.plus(e2) }

            val status = Status(
                currentTask,
                currentTaskDuration,
                currentTaskDurationToday,
                totalDurationToday
            )

            output.status(status)
        } catch (e: Exception) {
            output.error(e)
        }
    }

    fun listTags() {
        val tags = taskDatabase.listTags()
        output.listTags(tags)
    }

}