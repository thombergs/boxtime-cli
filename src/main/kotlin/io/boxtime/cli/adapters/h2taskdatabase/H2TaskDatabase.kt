package io.boxtime.cli.adapters.h2taskdatabase

import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.taskdatabase.TaskDatabase
import org.springframework.stereotype.Component
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Component
class H2TaskDatabase(
    private val taskRepository: TaskRepository
) : TaskDatabase {

    override fun listTasks(): List<Task> {
        return StreamSupport.stream(taskRepository.findAll().spliterator(), false)
            .map { it.toDomainObject() }
            .collect(Collectors.toList())
    }

    override fun findTaskById(id: String): Task? {
        return taskRepository.findById(id)
            .map { it.toDomainObject() }
            .orElse(null)
    }

    override fun findTaskByIdStartsWith(id: String): List<Task> {
        return taskRepository.findByIdStartsWith(id)
            .map { it.toDomainObject() }
    }

    override fun addTask(task: Task) {
        taskRepository.save(TaskEntity.fromDomainObject(task, new = true))
    }

    override fun reset() {
        taskRepository.deleteAll()
    }


}