package io.boxtime.cli.adapters.alfred

import io.boxtime.cli.application.ReportData
import io.boxtime.cli.application.toReadableString
import io.boxtime.cli.ports.output.Output
import io.boxtime.cli.ports.taskdatabase.Tag
import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.tasklogger.Count
import io.boxtime.cli.ports.tasklogger.LogEntry
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component

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

    override fun canOnlyTrackTasksWithTimeUnit(task: Task) {
        printItems(
            ScriptFilterItem(
                task.id,
                "Cannot track task.",
                "Can only track tasks with time unit. This task has unit '${task.unit.name}'."
            )
        )
    }

    override fun taskStopped(task: Task, logEntry: LogEntry) {
        val count = task.toCount(logEntry.count!!)
        printItems(
            ScriptFilterItem(
                task.id,
                "Task stopped after ${count?.asDuration()?.toReadableString()}.",
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
                    "Unit: ${it.unit.name}." + it.tagsString()?.let { " Tags: $it" }
                )
            }
            .toTypedArray()
        )
    }

    override fun listTags(tags: List<Tag>) {
        printItems(*tags
            .map {
                ScriptFilterItem(
                    it.nameWithHashtag(),
                    it.name,
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

    override fun taskLogged(task: Task, count: Count) {
        printItems(
            ScriptFilterItem(
                task.id,
                "Logged $count.",
                "Logged work for task '${task.title}'."
            )
        )
    }

    override fun report(reportData: ReportData) {
        val items = mutableListOf<ScriptFilterItem>()
        if (reportData.currentTask != null) {
            items.add(
                ScriptFilterItem(
                    "void",
                    reportData.currentTask.task.title,
                    "Currently tracking. Session time: ${reportData.currentTask.count}."
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

        items.add(
            ScriptFilterItem(
                "void",
                "Total time logged today",
                reportData.totalTimeLogged.toReadableString()
            )
        )

        for (task in reportData.loggedTasks) {
            items.add(
                ScriptFilterItem(
                    "void",
                    task.task.title,
                    "Logged ${task.count} today."
                )
            )
        }

        printItems(
            *items.toTypedArray()
        )
    }
}