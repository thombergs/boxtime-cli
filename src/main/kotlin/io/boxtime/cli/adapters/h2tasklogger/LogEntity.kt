package io.boxtime.cli.adapters.h2tasklogger

import io.boxtime.cli.ports.tasklogger.LogEntry
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Table("LOG")
data class LogEntity(
    @Id
    @Column("ID")
    var databaseId: Int?,
    @Column("TASK_ID")
    var taskId: String,
    @Column("START_TIME")
    var start: LocalDateTime,
    @Column("END_TIME")
    var end: LocalDateTime?,
    @Column("COUNT")
    var count: Float?
) : Persistable<Int> {

    constructor(taskId: String, start: LocalDateTime, end: LocalDateTime) : this(
        null,
        taskId,
        start,
        end,
        ChronoUnit.SECONDS.between(start, end).toFloat()
    )

    constructor(taskId: String) : this(
        null,
        taskId,
        LocalDateTime.now(),
        null,
        null
    )

    constructor(taskId: String, count: Int) : this(
        null,
        taskId,
        LocalDateTime.now(),
        null,
        count.toFloat()
    )

    fun stop() {
        this.end = LocalDateTime.now()
        this.count = ChronoUnit.SECONDS.between(start, end).toFloat()
    }

    override fun getId(): Int? {
        return this.databaseId
    }

    override fun isNew(): Boolean {
        return this.databaseId == null
    }

    fun toDomainObject(): LogEntry {
        return LogEntry(
            this.taskId,
            this.start,
            this.count.let { it }
        )
    }

}

