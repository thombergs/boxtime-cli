package io.boxtime.cli.adapters.h2tasklogger

import io.boxtime.cli.ports.tasklogger.LogEntry
import io.boxtime.cli.ports.tasklogger.TaskLogger
import org.springframework.stereotype.Component
import java.time.Duration
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

    override fun stop(): String? {
        val openLog = logRepository.findOpenLog().orElse(null)
            ?: return null

        openLog.stop()
        logRepository.save(openLog)
        return openLog.taskId
    }

    override fun logDuration(taskId: String, duration: Duration) {
        val log = LogEntity(
            null,
            taskId,
            LocalDateTime.now(),
            LocalDateTime.now(),
            duration.toSeconds().toInt()
        )
        logRepository.save(log)
    }

    override fun getLogEntries(): List<LogEntry> {
        return logRepository.findAll()
            .map { it.toDomainObject() }

    }

    override fun getLogEntriesFromToday(taskId: String?): List<LogEntry> {

        val start = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)
        val end = LocalDateTime.MAX

        if (taskId == null) {
            return logRepository.findLogEntriesStartedInInverval(
                start,
                end
            ).map { it.toDomainObject() }
        }

        return logRepository.findLogEntriesByTaskStartedInInverval(
            taskId,
            start,
            end
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