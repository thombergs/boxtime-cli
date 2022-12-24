package io.boxtime.cli.application

import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.taskdatabase.TaskDatabase
import io.boxtime.cli.ports.tasklogger.TaskLogger
import io.boxtime.cli.ports.userlog.UserLog
import org.springframework.stereotype.Component

/**
 * Central facade for all use cases.
 */
@Component
class Application(
    private val taskDatabase: TaskDatabase,
    private val taskLogger: TaskLogger,
    private val userLog: UserLog,
) {

    private val durationParser: DurationParser = DurationParser()

    fun addTask(title: String) {
        try {
            val task = Task(title);
            taskDatabase.addTask(task)
            userLog.taskAdded(task)
        } catch (e: Exception) {
            userLog.error(e)
        }
    }

    fun startTask(taskId: String) {
        try {
            stopTask()
            val task = taskDatabase.findTaskById(taskId)
            if (task == null) {
                userLog.taskNotFound(taskId)
                return
            }
            taskLogger.start(taskId)
            userLog.taskStarted(task)
        } catch (e: Exception) {
            userLog.error(e)
        }
    }

    fun stopTask() {
        try {
            val taskId = taskLogger.stop()
            if (taskId == null) {
                userLog.notCurrentlyTracking()
                return
            }
            val task = taskDatabase.findTaskById(taskId)
            if (task == null) {
                userLog.taskNotFound(taskId)
                return
            }
            userLog.taskStopped(task)
        } catch (e: Exception) {
            userLog.error(e)
        }
    }

    fun listTasks() {
        try {
            userLog.listTasks(taskDatabase.listTasks())
        } catch (e: Exception) {
            userLog.error(e)
        }
    }

    fun resetTasks() {
        try {
            taskDatabase.reset()
            userLog.tasksReset()
        } catch (e: Exception) {
            userLog.error(e)
        }
    }

    fun resetLog() {
        try {
            taskLogger.reset()
            userLog.logsReset()
        } catch (e: Exception) {
            userLog.error(e)
        }
    }

    fun logTask(taskId: String, durationString: String) {
        try {
            val task = taskDatabase.findTaskById(taskId)
            if (task == null) {
                userLog.taskNotFound(taskId)
                return
            }
            val duration = durationParser.parse(durationString)
            taskLogger.logDuration(taskId, duration)
            userLog.taskLogged(task, durationString)
        } catch (e: Exception) {
            userLog.error(e)
        }

    }

}