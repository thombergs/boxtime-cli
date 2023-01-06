package io.boxtime.cli.application

import io.boxtime.cli.ports.output.Output
import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.taskdatabase.TaskDatabase
import io.boxtime.cli.ports.taskdatabase.Unit
import io.boxtime.cli.ports.tasklogger.Count
import io.boxtime.cli.ports.tasklogger.TaskLogger
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
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

    fun addTask(title: String, unit: String, extractTags: Boolean) {
        try {
            val task = Task(title, Unit(unit), extractTags);
            taskDatabase.addTask(task)
            output.taskAdded(task)
        } catch (e: Exception) {
            output.error(e)
        }
    }

    fun startTask(taskId: String) {
        try {
            val task = getTaskStartingWith(taskId) ?: return

            if (task.unit != Unit.SECONDS) {
                output.canOnlyTrackTasksWithTimeUnit(task)
                return
            }

            stopTask(silent = true)
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
                if (!silent) output.taskNotFound(logEntry.taskId)
                return
            }
            output.taskStopped(task, logEntry)
        } catch (e: Exception) {
            output.error(e)
        }
    }

    fun listTasks(
        count: Int = 10,
        nameFilter: String = "",
        requiredUnits: List<String>,
        rejectedUnits: List<String>
    ) {
        try {
            val filter = TaskFilter(
                nameFilter,
                requiredUnits,
                rejectedUnits,
                count
            )
            output.listTasks(taskDatabase.listTasks(filter))
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

    fun logTask(taskId: String, completion: String) {
        try {
            val task = getTaskStartingWith(taskId) ?: return
            val count = task.toCount(completion)
            taskLogger.logCount(task.id, count)
            output.taskLogged(task, count)
        } catch (e: Exception) {
            output.error(e)
        }
    }

    companion object {
        fun getAlfredWorkflowsFolder(): File {
            return File("${System.getProperty("user.home")}/Library/Application Support/Alfred/Alfred.alfredpreferences/workflows")
        }

        fun getBoxtimeWorkflowFolder(): File {
            return File(getAlfredWorkflowsFolder(), "boxtime")
        }
    }

    fun installAlfredWorkflow() {
        if (isAlfredInstalled()) {
            createBoxtimeWorkflowFolder()
            copyResource("/alfred/icon.png", getBoxtimeWorkflowFolder())
            copyResource("/alfred/info.plist", getBoxtimeWorkflowFolder())
        }
    }

    private fun copyResource(resourcePath: String, targetFolder: File){
        val inputStream = this::class.java.getResourceAsStream(resourcePath)
        val filename = resourcePath.substring(resourcePath.lastIndexOf('/') + 1)
        Files.copy(inputStream!!, Path.of(targetFolder.absolutePath, filename))
    }

    private fun createBoxtimeWorkflowFolder() {
        val boxtimeWorkflowFolder = getBoxtimeWorkflowFolder().toPath()
        Files.createDirectories(boxtimeWorkflowFolder)
    }

    private fun isAlfredInstalled(): Boolean {
        return getAlfredWorkflowsFolder().exists() && getAlfredWorkflowsFolder().isDirectory
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

            val currentTask = taskLogger.getCurrentLogEntry()
                ?.let {
                    val task = taskDatabase.findTaskById(it.taskId)!!
                    val currentSessionDuration =
                        Duration.between(it.startTime, LocalDateTime.now()).toSeconds().toFloat()
                    val currentSessionCount = Count(task.unit, currentSessionDuration)
                    TaskWithCount(task, currentSessionCount)
                }

            val todaysLogEntries = taskLogger.getLogEntriesFromToday()
            val todaysTasks = todaysLogEntries
                .map {
                    val task = taskDatabase.findTaskById(it.taskId)!!
                    if (it.taskId == currentTask?.task?.id && it.isOpen()) {
                        // add the current session length to the current task's tally
                        TaskWithCount(task, currentTask.count)
                    }
                    TaskWithCount(task, Count(task.unit, it.count ?: 0f))
                }
                .groupBy { it.task.id }
                .map {
                    it.value.reduce { left, right ->
                        TaskWithCount(left.task, Count(left.task.unit, left.count.count + right.count.count))
                    }
                }

            val totalSecondsTrackedToday = todaysTasks
                .filter { it.count.unit == Unit.SECONDS }
                .sumOf { it.count.count.toLong() }

            val status = Status(
                currentTask,
                todaysTasks,
                Duration.ofSeconds(totalSecondsTrackedToday),
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