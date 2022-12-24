package io.boxtime.cli.commands.log

import org.springframework.stereotype.Component
import picocli.CommandLine.Command

@Component
@Command(
    name = "log",
    mixinStandardHelpOptions = true,
    subcommands = [
        ResetLogsCommand::class,
    ],
    description = ["Commands to manage your time log."]
)
class LogCommand(
)