package io.boxtime.cli.commands.track

import org.springframework.stereotype.Component
import picocli.CommandLine.Command

@Component
@Command(
    name = "track",
    mixinStandardHelpOptions = true,
    subcommands = [
        StartTaskCommand::class,
        StopTaskCommand::class,
        LogTaskCommand::class
    ],
    description = ["Commands to track your time."]
)
class TrackCommand(
)