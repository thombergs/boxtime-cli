package io.boxtime.cli.commands.track

import io.boxtime.cli.commands.mixins.BaseCommand
import io.boxtime.cli.config.ApplicationFactory
import org.springframework.stereotype.Component
import picocli.CommandLine.Command

@Component
@Command(
    name = "stop",
    description = ["Stop tracking."]
)
class StopTaskCommand(
    applicationFactory: ApplicationFactory
) : BaseCommand(applicationFactory) {

    override fun call(): Int {
        getApplication().stopTask()
        return 0
    }

}