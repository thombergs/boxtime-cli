package io.boxtime.cli.adapters.plaintextoutput

import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.output.Output
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

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

    override fun taskStarted(task: Task) {
        LOGGER.info("Started tracking task '${task.title}'.")
    }

    override fun taskStopped(task: Task) {
        LOGGER.info("Stopped tracking task '${task.title}'.")
    }

    override fun notCurrentlyTracking() {
        LOGGER.info("Not currently tracking a task.")
    }

    override fun listTasks(tasks: List<Task>) {
        LOGGER.info("Task ID".padEnd(36) + "| Task".padEnd(30))
        LOGGER.info("-".padEnd(36, '-') + "|-".padEnd(30, '-') + "|")
        for (task in tasks) {
            LOGGER.info("${task.id.padEnd(36)}| ${task.title.padEnd(30)}|")
        }
    }

    override fun tasksReset() {
        LOGGER.info("Deleted all tasks.")
    }

    override fun logsReset() {
        LOGGER.info("Deleted all time logs.")
    }

    override fun taskLogged(task: Task, durationString: String) {
        LOGGER.info("Logged '${durationString}' for task '${task.title}'")
    }
}