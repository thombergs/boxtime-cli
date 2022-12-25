package io.boxtime.cli.commands.log

import io.boxtime.cli.commands.mixins.BaseCommand
import io.boxtime.cli.config.ApplicationFactory
import org.springframework.stereotype.Component
import picocli.CommandLine.Command

@Component
@Command(
    name = "reset",
    description = ["Delete all time logs."]
)
class ResetLogsCommand(
    applicationFactory: ApplicationFactory
) : BaseCommand(applicationFactory) {

    override fun call(): Int {
        getApplication().resetLog()
        return 0
    }

}