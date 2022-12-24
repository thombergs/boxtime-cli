package io.boxtime.cli.adapters.h2taskdatabase

import io.boxtime.cli.ports.taskdatabase.Task
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceCreator
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("TASK")
data class TaskEntity(
    @Id
    @Column("ID")
    val databaseId: String,
    @Column("TITLE")
    val title: String,
    @Transient
    private val new: Boolean
) : Persistable<String> {

    @PersistenceCreator
    constructor(databaseId: String, title: String) : this(databaseId, title, false)

    companion object {
        fun fromDomainObject(task: Task, new: Boolean): TaskEntity {
            return TaskEntity(task.id, task.title, new)
        }
    }

    fun toDomainObject(): Task {
        return Task(this.databaseId, this.title)
    }

    override fun getId(): String {
        return this.databaseId
    }

    override fun isNew(): Boolean {
        return this.new
    }

}

