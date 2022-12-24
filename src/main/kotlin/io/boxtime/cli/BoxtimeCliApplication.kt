package io.boxtime.cli

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ImportRuntimeHints
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@ImportRuntimeHints(RuntimeHints::class)
@SpringBootApplication
@EnableJdbcRepositories
class BoxtimeCliApplication

fun main(args: Array<String>) {
    runApplication<BoxtimeCliApplication>(*args)
}
