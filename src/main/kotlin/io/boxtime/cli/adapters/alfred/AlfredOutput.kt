package io.boxtime.cli.adapters.alfred

import io.boxtime.cli.application.Status
import io.boxtime.cli.application.toReadableString
import io.boxtime.cli.ports.output.Output
import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.tasklogger.LogEntry
import org.springframework.stereotype.Component
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Component
class AlfredOutput : Output {

    private fun printItems(vararg items: ScriptFilterItem) {
        val content = ScriptFilterItems(items.toList())
        println(Json.encodeToString(content))
    }

    override fun error(e: Exception) {
        printItems(
            ScriptFilterItem(
                "void",
                "Error.",
                e.message ?: "Unspecified error."
            )
        )
    }

    override fun taskAdded(task: Task) {
        printItems(
            ScriptFilterItem(
                task.id,
                "Task added.",
                "You can track the task with the ID ${task.id}."
            )
        )
    }

    override fun taskNotFound(taskId: String) {
        printItems(
            ScriptFilterItem(
                taskId,
                "Task not found.",
                "Task with the ID $taskId not found."
            )
        )
    }

    override fun nonUniqueTaskId(taskId: String) {
        printItems(
            ScriptFilterItem(
                taskId,
                "Task not found.",
                "Multiple task IDs start with $taskId. Try adding some extra characters to make it unique."
            )
        )
    }

    override fun taskStarted(task: Task) {
        printItems(
            ScriptFilterItem(
                task.id,
                "Task started.",
                "Started tracking '${task.title}'."
            )
        )
    }

    override fun taskStopped(task: Task, logEntry: LogEntry) {
        printItems(
            ScriptFilterItem(
                task.id,
                "Task stopped after ${logEntry.duration?.toReadableString()}.",
                "Stopped tracking '${task.title}'."
            )
        )
    }

    override fun notCurrentlyTracking() {
        printItems(
            ScriptFilterItem(
                "void",
                "Not tracking.",
                "Not currently tracking a task."
            )
        )
    }

    override fun listTasks(tasks: List<Task>) {
        printItems(*tasks
            .map {
                ScriptFilterItem(
                    it.id,
                    it.title,
                    ""
                )
            }
            .toTypedArray()
        )
    }

    override fun tasksReset() {
        printItems(
            ScriptFilterItem(
                "void",
                "Deleted all tasks.",
                ""
            )
        )
    }

    override fun logsReset() {
        printItems(
            ScriptFilterItem(
                "void",
                "Deleted all time logs.",
                ""
            )
        )
    }

    override fun taskLogged(task: Task, durationString: String) {
        printItems(
            ScriptFilterItem(
                task.id,
                "Logged ${durationString}.",
                "Logged time for task '${task.title}'."
            )
        )
    }

    override fun status(status: Status) {
        val items = mutableListOf<ScriptFilterItem>()
        if (status.currentTask != null) {
            items.add(
                ScriptFilterItem(
                    "void",
                    "Currently tracking task:",
                    status.currentTask.title
                )
            )
            items.add(
                ScriptFilterItem(
                    "void",
                    "Time tracked for current session:",
                    "${status.currentTaskDuration?.toReadableString()}"
                )
            )
            items.add(
                ScriptFilterItem(
                    "void",
                    "Time tracked for this task today:",
                    "${status.currentTaskDurationToday?.toReadableString()}"
                )
            )
        } else {
            items.add(
                ScriptFilterItem(
                    "void",
                    "Currently not tracking a task",
                    ""
                )
            )
        }

        if (status.totalDurationToday != null) {
            items.add(
                ScriptFilterItem(
                    "void",
                    "Total time tracked today:",
                    "${status.totalDurationToday.toReadableString()}"
                )
            )
        } else {
            items.add(
                ScriptFilterItem(
                    "void",
                    "No time tracked today, yet.",
                    ""
                )
            )
        }

        printItems(
            *items.toTypedArray()
        )
    }
}