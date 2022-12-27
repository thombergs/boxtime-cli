package io.boxtime.cli.adapters.h2taskdatabase

import org.springframework.data.repository.CrudRepository

interface TaskRepository : CrudRepository<TaskEntity, String> {

    fun findByIdStartingWith(id: String): List<TaskEntity>

}