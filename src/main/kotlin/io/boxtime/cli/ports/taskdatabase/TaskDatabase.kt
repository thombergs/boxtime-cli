package io.boxtime.cli.ports.taskdatabase

interface TaskDatabase {

    fun listTasks(count: Int = 10, filter: String? = null): List<Task>

    fun findTaskById(id: String): Task?

    fun findTaskByIdStartsWith(id: String): List<Task>

    fun addTask(task: Task)

    fun reset()

    fun listTags(filter: String? = null): List<Tag>

}