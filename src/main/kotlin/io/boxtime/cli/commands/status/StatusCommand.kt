package io.boxtime.cli.commands.status

import io.boxtime.cli.commands.mixins.BaseCommand
import io.boxtime.cli.config.ApplicationFactory
import org.springframework.stereotype.Component
import picocli.CommandLine.Command

@Component
@Command(
    name = "status",
    description = ["Output current status."]
)
class StatusCommand(
    applicationFactory: ApplicationFactory
) : BaseCommand(applicationFactory) {

    override fun call(): Int {
        getApplication().status()
        return 0
    }

}