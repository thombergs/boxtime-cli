package io.boxtime.cli.adapters.h2tasklogger

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface LogRepository : CrudRepository<LogEntity, String> {

    @Query("select * from LOG l where l.END_TIME is null")
    fun findOpenLog(): Optional<LogEntity>

}