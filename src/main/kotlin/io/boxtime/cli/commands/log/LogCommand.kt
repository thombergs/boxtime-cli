package io.boxtime.cli.commands.log

import org.springframework.stereotype.Component
import picocli.CommandLine.Command

@Component
@Command(
    name = "log",
    subcommands = [
        ResetLogsCommand::class,
    ],
    mixinStandardHelpOptions = true,
    description = ["Commands to manage your time log."]
)
class LogCommand(
)