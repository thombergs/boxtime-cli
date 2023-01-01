package io.boxtime.cli.adapters.h2taskdatabase

import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.taskdatabase.Unit
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceCreator
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("TASK")
data class TaskEntity(
    @Id
    @Column("ID")
    private val id: String,
    @Column("TITLE")
    val title: String,
    @Column("CREATED_DATE")
    val created: LocalDateTime,
    @Column("UNIT")
    val unit: String,
    @MappedCollection(idColumn = "TASK_ID")
    val tags: Set<TagRef> = HashSet(),
    @Transient
    private val new: Boolean
) : Persistable<String> {

    @PersistenceCreator
    constructor(id: String, title: String, created: LocalDateTime, unit: String, tags: Set<TagRef>) : this(id, title, created, unit, tags,false)

    companion object {
        fun fromDomainObject(task: Task, new: Boolean): TaskEntity {
            val tagRefs = task.tags
                .map { TagRef(it.id) }
                .toSet()
            return TaskEntity(task.id, task.title, task.created, task.unit.name, tagRefs, new)
        }
    }

    fun toDomainObject(tagRepository: TagRepository): Task {
        val tags = tagRepository.findByIdIn(this.tags.map { it.tagId })
            .map { it.toDomainObject() }
            .toSet()
        return Task(this.id, this.title, this.created, Unit(this.unit), tags)
    }

    override fun getId(): String {
        return this.id
    }

    override fun isNew(): Boolean {
        return this.new
    }

}

