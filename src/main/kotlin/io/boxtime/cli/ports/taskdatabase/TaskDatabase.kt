package io.boxtime.cli.ports.taskdatabase

import io.boxtime.cli.application.TaskFilter

interface TaskDatabase {

    fun listTasks(filter: TaskFilter = TaskFilter()): List<Task>

    fun findTaskById(id: String): Task?

    fun findTaskByIdStartsWith(id: String): List<Task>

    fun addTask(task: Task)

    fun reset()

    fun listTags(filter: String? = null): List<Tag>

}