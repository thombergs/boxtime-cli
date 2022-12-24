package io.boxtime.cli.ports.taskdatabase

import org.springframework.data.annotation.Id
import java.util.*

data class Task(
    @Id
    val id: String,
    val title: String
) {

    constructor(title: String) : this(UUID.randomUUID().toString(), title)

}
