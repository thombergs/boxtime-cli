package io.boxtime.cli.ports.taskdatabase

interface TaskDatabase {

    fun listTasks(): List<Task>

    fun findTaskById(id: String): Task?

    fun addTask(task: Task)

    fun reset()

}