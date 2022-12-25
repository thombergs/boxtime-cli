package io.boxtime.cli.config

import io.boxtime.cli.adapters.alfredoutput.AlfredOutput
import io.boxtime.cli.adapters.h2taskdatabase.H2TaskDatabase
import io.boxtime.cli.adapters.h2tasklogger.H2TaskLogger
import io.boxtime.cli.adapters.plaintextoutput.PlainTextOutput
import io.boxtime.cli.application.Application
import org.springframework.stereotype.Component

@Component
class ApplicationFactory(
    private val taskDatabase: H2TaskDatabase,
    private val taskLogger: H2TaskLogger,
    private val plainTextOutput: PlainTextOutput,
    private val alfredOutput: AlfredOutput
) {

    fun createApplication(config: ApplicationConfig? = null): Application {
        return Application(
            taskDatabase,
            taskLogger,
            if (config?.alfred == true) alfredOutput
            else plainTextOutput
        )
    }
}

data class ApplicationConfig(
    val alfred: Boolean
)
