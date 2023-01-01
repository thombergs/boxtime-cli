package io.boxtime.cli.adapters.h2taskdatabase

import io.boxtime.cli.application.TaskFilter
import io.boxtime.cli.ports.taskdatabase.Tag
import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.taskdatabase.TaskDatabase
import org.springframework.stereotype.Component

@Component
class H2TaskDatabase(
    private val taskRepository: TaskRepository,
    private val tagRepository: TagRepository
) : TaskDatabase {

    override fun listTasks(filter: TaskFilter): List<Task> {
        val requiredUnits = filter.requiredUnits.ifEmpty { null }
        val rejectedUnits = filter.rejectedUnits.ifEmpty { null }
        return taskRepository.findTasks(filter.count, filter.name, requiredUnits, rejectedUnits)
            .map { it.toDomainObject(tagRepository) }
    }

    override fun findTaskById(id: String): Task? {
        return taskRepository.findById(id)
            .map { it.toDomainObject(tagRepository) }
            .orElse(null)
    }

    override fun findTaskByIdStartsWith(id: String): List<Task> {
        return taskRepository.findByIdStartingWith(id)
            .map { it.toDomainObject(tagRepository) }
    }

    override fun addTask(task: Task) {

        val sanitizedTags = task.tags
            .map { addTag(it.name) }
            .toSet()

        val sanitizedTask = task.withTags(sanitizedTags)

        taskRepository.save(TaskEntity.fromDomainObject(sanitizedTask, new = true))
    }

    override fun reset() {
        taskRepository.deleteAll()
    }

    private fun addTag(tag: String): Tag {
        val existingTag = tagRepository.findByName(tag)
        if (existingTag != null) {
            return existingTag.toDomainObject();
        }

        val newTag = Tag(tag)
        return tagRepository.save(TagEntity.fromDomainObject(newTag, new = true))
            .toDomainObject()
    }

    override fun listTags(filter: String?): List<Tag> {
        return if (filter == null) {
            tagRepository.findAll()
                .map { it.toDomainObject() }
        } else {
            tagRepository.findByNameLike(filter)
                .map { it.toDomainObject() }
        }
    }

}