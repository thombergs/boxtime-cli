package io.boxtime.cli.commands.alfred

import org.springframework.stereotype.Component
import picocli.CommandLine.Command

@Component
@Command(
    name = "alfred",
    subcommands = [
        InstallAlfredWorkflowCommand::class,
    ],
    mixinStandardHelpOptions = true,
    description = ["Commands to manage the Boxtime Alfred workflow."]
)
class AlfredCommand(
)