package io.boxtime.cli.adapters.h2taskdatabase

import org.springframework.data.repository.CrudRepository

interface TagRepository : CrudRepository<TagEntity, String> {

    fun findByName(name: String): TagEntity?

    fun findByNameLike(name: String): List<TagEntity>

    fun findByIdIn(names: List<String>): List<TagEntity>

}