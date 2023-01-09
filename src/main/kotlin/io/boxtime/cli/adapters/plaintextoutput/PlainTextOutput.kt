package io.boxtime.cli.adapters.plaintextoutput

import io.boxtime.cli.application.ReportData
import io.boxtime.cli.application.format
import io.boxtime.cli.application.toReadableString
import io.boxtime.cli.ports.output.Output
import io.boxtime.cli.ports.taskdatabase.Tag
import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.tasklogger.Count
import io.boxtime.cli.ports.tasklogger.LogEntry
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream

@Component
class PlainTextOutput : Output {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger("UserLog")
    }

    override fun error(e: Exception) {
        LOGGER.error("Error:", e)
    }

    override fun taskAdded(task: Task) {
        LOGGER.info("Added task '${task.title}' with ID '${task.id}'.")
    }

    override fun taskNotFound(taskId: String) {
        LOGGER.info("No task with id '${taskId}'.")
    }

    override fun nonUniqueTaskId(taskId: String) {
        LOGGER.info("Multiple task IDs start with $taskId. Try adding some extra characters to make it unique.")
    }

    override fun taskStarted(task: Task) {
        LOGGER.info("Started tracking task '${task.title}'.")
    }

    override fun canOnlyTrackTasksWithTimeUnit(task: Task) {
        LOGGER.info("Cannot track task '${task.title}'. Only tasks with time unit can be tracked. This task has unit '${task.unit.name}'.")
    }

    override fun taskStopped(task: Task, logEntry: LogEntry) {
        val count = task.toCount(logEntry.count!!)
        LOGGER.info("Stopped tracking task '${task.title}' after ${count?.asDuration()?.toReadableString()}.")
    }

    override fun notCurrentlyTracking() {
        LOGGER.info("Not currently tracking a task.")
    }

    override fun listTasks(tasks: List<Task>) {
        val out = ByteArrayOutputStream()

        val table = Table(
            listOf(
                Column("Task ID"),
                Column("Task"),
                Column("Unit"),
            )
        )

        for (task in tasks) {
            table.addRow(task.id, task.title, task.unit.name)
        }

        table.print(out)

        LOGGER.info(String(out.toByteArray()))
    }

    override fun listTags(tags: List<Tag>) {
        val out = ByteArrayOutputStream()

        val table = Table(
            listOf(
                Column("Tag ID"),
                Column("Tag"),
            )
        )

        for (tag in tags) {
            table.addRow(tag.id, tag.nameWithHashtag())
        }

        table.print(out)

        LOGGER.info(String(out.toByteArray()))
    }

    override fun tasksReset() {
        LOGGER.info("Deleted all tasks.")
    }

    override fun logsReset() {
        LOGGER.info("Deleted all time logs.")
    }

    override fun taskLogged(task: Task, count: Count) {
        LOGGER.info("Logged $count for task '${task.title}'")
    }

    override fun report(reportData: ReportData) {
        LOGGER.info("Report from ${reportData.from.format()} to ${reportData.to.format()}")
        if (reportData.currentTask != null) {
            LOGGER.info("Currently tracking '${reportData.currentTask.task.title}'.")
            LOGGER.info("Current session duration: ${reportData.currentTask.count}.")
        } else {
            LOGGER.info("Currently not tracking a task.")
        }

        LOGGER.info("Total duration tracked: ${reportData.totalTimeLogged.toReadableString()}.")

        for (task in reportData.loggedTasks.sortedBy { it.count }) {
            LOGGER.info("Logged ${task.count} for task '${task.task.title}'.")
        }
    }
}