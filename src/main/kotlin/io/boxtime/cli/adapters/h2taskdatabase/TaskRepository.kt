package io.boxtime.cli.adapters.h2taskdatabase

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface TaskRepository : CrudRepository<TaskEntity, String> {

    @Query("select * from TASK where ID like concat(:id, '%')")
    fun findByIdStartsWith(@Param("id") id: String): List<TaskEntity>

}