package io.boxtime.cli.adapters.h2tasklogger

import io.boxtime.cli.ports.tasklogger.LogEntry
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Duration
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
    @Column("DURATION_IN_SECONDS")
    var durationInSeconds: Int?
) : Persistable<Int> {

    constructor(taskId: String, start: LocalDateTime, end: LocalDateTime) : this(
        null,
        taskId,
        start,
        end,
        ChronoUnit.SECONDS.between(start, end).toInt()
    )

    constructor(taskId: String) : this(
        null,
        taskId,
        LocalDateTime.now(),
        null,
        null
    )

    constructor(taskId: String, durationInSeconds: Int) : this(
        null,
        taskId,
        LocalDateTime.now(),
        null,
        durationInSeconds
    )

    fun stop() {
        this.end = LocalDateTime.now()
        this.durationInSeconds = ChronoUnit.SECONDS.between(start, end).toInt()
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
            this.durationInSeconds?.let { Duration.ofSeconds(it.toLong()) })
    }

}

