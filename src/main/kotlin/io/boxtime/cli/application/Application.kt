package io.boxtime.cli.application

import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.taskdatabase.TaskDatabase
import io.boxtime.cli.ports.tasklogger.TaskLogger
import io.boxtime.cli.ports.output.Output
import org.springframework.stereotype.Component

/**
 * Central facade for all use cases.
 */
@Component
class Application(
    private val taskDatabase: TaskDatabase,
    private val taskLogger: TaskLogger,
    private val output: Output,
) {

    private val durationParser: DurationParser = DurationParser()

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
            stopTask()
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

    fun stopTask() {
        try {
            val taskId = taskLogger.stop()
            if (taskId == null) {
                output.notCurrentlyTracking()
                return
            }
            val task = taskDatabase.findTaskById(taskId)
            if (task == null) {
                output.taskNotFound(taskId)
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
            output.error(e)
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
            val duration = durationParser.parse(durationString)
            taskLogger.logDuration(taskId, duration)
            output.taskLogged(task, durationString)
        } catch (e: Exception) {
            output.error(e)
        }

    }

}