package io.boxtime.cli.adapters.h2taskdatabase

import io.boxtime.cli.ports.taskdatabase.Tag
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceCreator
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("TAG")
data class TagEntity(
    @Id
    @Column("ID")
    private val id: String,
    @Column("NAME")
    val name: String,
    @Transient
    private val new: Boolean
) : Persistable<String> {

    @PersistenceCreator
    constructor(id: String, name: String) : this(id, name, false)

    companion object {
        fun fromDomainObject(tag: Tag, new: Boolean): TagEntity {
            return TagEntity(tag.id, tag.name, new)
        }
    }

    fun toDomainObject(): Tag {
        return Tag(this.id, this.name)
    }

    override fun getId(): String {
        return this.id
    }

    override fun isNew(): Boolean {
        return this.new
    }

}

