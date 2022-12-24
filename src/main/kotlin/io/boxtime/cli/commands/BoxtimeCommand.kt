package io.boxtime.cli.commands

import io.boxtime.cli.commands.log.LogCommand
import io.boxtime.cli.commands.status.StatusCommand
import io.boxtime.cli.commands.task.TaskCommand
import io.boxtime.cli.commands.track.TrackCommand
import org.springframework.stereotype.Component
import picocli.CommandLine.Command

@Component
@Command(
    name = "boxtime", mixinStandardHelpOptions = true, subcommands = [
        TaskCommand::class,
        TrackCommand::class,
        LogCommand::class,
        StatusCommand::class
    ]
)
class BoxtimeCommand(
)