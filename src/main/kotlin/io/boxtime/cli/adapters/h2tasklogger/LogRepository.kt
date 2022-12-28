package io.boxtime.cli.adapters.h2tasklogger

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime
import java.util.*

interface LogRepository : CrudRepository<LogEntity, String> {

    @Query("select * from LOG l where l.END_TIME is null")
    fun findOpenLog(): Optional<LogEntity>

    @Query(
        """
        select 
            * 
        from 
            LOG l 
        where 
            l.TASK_ID = :taskId and 
            (l.START_TIME >= :start and l.START_TIME <= :end)
        """
    )
    fun findLogEntriesByTaskStartedInInverval(
        @Param("taskId") taskId: String,
        @Param("start") intervalStart: LocalDateTime,
        @Param("end") intervalEnd: LocalDateTime
    ): List<LogEntity>

    @Query(
        """
        select 
            * 
        from 
            LOG l 
        where 
            l.START_TIME >= :start and l.START_TIME <= :end
        """
    )
    fun findLogEntriesStartedInInverval(
        @Param("start") intervalStart: LocalDateTime,
        @Param("end") intervalEnd: LocalDateTime
    ): List<LogEntity>

}