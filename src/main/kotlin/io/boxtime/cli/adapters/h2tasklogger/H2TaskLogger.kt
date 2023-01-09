package io.boxtime.cli.adapters.h2tasklogger

import io.boxtime.cli.ports.tasklogger.Count
import io.boxtime.cli.ports.tasklogger.LogEntry
import io.boxtime.cli.ports.tasklogger.TaskLogger
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Component
class H2TaskLogger(
    private val logRepository: LogRepository
) : TaskLogger {

    override fun start(taskId: String) {
        val log = LogEntity(taskId)
        logRepository.save(log)
    }

    override fun stop(): LogEntry? {
        val openLog = logRepository.findOpenLog().orElse(null)
            ?: return null

        openLog.stop()
        logRepository.save(openLog)
        return openLog.toDomainObject()
    }

    override fun logCount(taskId: String, count: Count) {
        val log = LogEntity(
            null,
            taskId,
            LocalDateTime.now(),
            LocalDateTime.now(),
            count.count.toFloat()
        )
        logRepository.save(log)
    }

    override fun getLogEntries(): List<LogEntry> {
        return logRepository.findAll()
            .map { it.toDomainObject() }

    }

    override fun getLogEntries(
        from: LocalDateTime,
        to: LocalDateTime,
        taskId: String?
    ): List<LogEntry> {

        if (taskId == null) {
            return logRepository.findLogEntriesStartedInInverval(
                from,
                to
            ).map { it.toDomainObject() }
        }

        return logRepository.findLogEntriesByTaskStartedInInverval(
            taskId,
            from,
            to
        ).map { it.toDomainObject() }
    }

    override fun getCurrentLogEntry(): LogEntry? {
       return logRepository.findOpenLog().orElse(null)
           ?.toDomainObject()
    }

    override fun getCurrentTaskId(): String? {
        val openLog = logRepository.findOpenLog().orElse(null)
            ?: return null

        return openLog.taskId
    }

    override fun reset() {
        logRepository.deleteAll()
    }


}