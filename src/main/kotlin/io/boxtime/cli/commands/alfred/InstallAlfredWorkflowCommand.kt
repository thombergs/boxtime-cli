package io.boxtime.cli.commands.alfred

import io.boxtime.cli.commands.mixins.BaseCommand
import io.boxtime.cli.config.ApplicationFactory
import org.springframework.stereotype.Component
import picocli.CommandLine.Command

@Component
@Command(
    name = "install",
    description = ["Install the alfred workflow."]
)
class InstallAlfredWorkflowCommand(
    applicationFactory: ApplicationFactory,
) : BaseCommand(applicationFactory) {

    override fun call(): Int {
        getApplication().installAlfredWorkflow()
        return 0
    }

}