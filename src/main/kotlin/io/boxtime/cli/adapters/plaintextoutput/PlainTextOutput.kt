package io.boxtime.cli.adapters.plaintextoutput

import io.boxtime.cli.application.Status
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

    override fun status(status: Status) {
        if (status.currentTask != null) {
            LOGGER.info("Currently tracking '${status.currentTask.title}'.")
            LOGGER.info("Current session duration: ${status.currentTaskDuration?.toReadableString()}.")
            LOGGER.info("Total time tracked for this task today: ${status.currentTaskDurationToday?.toReadableString()}.")
        } else {
            LOGGER.info("Currently not tracking a task.")
        }

        if (status.totalDurationToday != null) {
            LOGGER.info("Total duration tracked today: ${status.totalDurationToday.toReadableString()}.")
        } else {
            LOGGER.info("No time tracked today, yet.")
        }

        for (task in status.nonTimeBasedTasksToday) {
            LOGGER.info("Logged ${task.count} for task '${task.task.title}'.")
        }
    }
}