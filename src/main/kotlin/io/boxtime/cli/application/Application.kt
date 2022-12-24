package io.boxtime.cli.application

import io.boxtime.cli.ports.output.Output
import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.taskdatabase.TaskDatabase
import io.boxtime.cli.ports.tasklogger.TaskLogger
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime

/**
 * Central facade for all use cases.
 */
@Component
class Application(
    private val taskDatabase: TaskDatabase,
    private val taskLogger: TaskLogger,
    private val output: Output,
) {

    fun addTask(title: String) {
        try {
            val task = Task(title);
            taskDatabase.addTask(task)
            output.taskAdded(task)
        } catch (e: Exception) {
            output.error(e)
        }
    }

    fun startTask(taskId: String) {
        try {
            stopTask(silent = true)
            val task = taskDatabase.findTaskById(taskId)
            if (task == null) {
                output.taskNotFound(taskId)
                return
            }
            taskLogger.start(taskId)
            output.taskStarted(task)
        } catch (e: Exception) {
            output.error(e)
        }
    }

    fun stopTask(silent: Boolean = false) {
        try {
            val taskId = taskLogger.stop()
            if (taskId == null) {
                if (!silent) output.notCurrentlyTracking()
                return
            }
            val task = taskDatabase.findTaskById(taskId)
            if (task == null) {
                if(!silent) output.taskNotFound(taskId)
                return
            }
            output.taskStopped(task)
        } catch (e: Exception) {
            output.error(e)
        }
    }

    fun listTasks() {
        try {
            output.listTasks(taskDatabase.listTasks())
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
            val task = taskDatabase.findTaskById(taskId)
            if (task == null) {
                output.taskNotFound(taskId)
                return
            }
            val duration = parseDuration(durationString)
            taskLogger.logDuration(taskId, duration)
            output.taskLogged(task, durationString)
        } catch (e: Exception) {
            output.error(e)
        }
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
                ?.map { it.duration ?: Duration.between(it.startTime, LocalDateTime.now()) }
                ?.fold(Duration.ZERO) { e1, e2 -> if (e2 == null) e1 else e1.plus(e2) }

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

}