package io.boxtime.cli.commands

import io.boxtime.cli.commands.alfred.AlfredCommand
import io.boxtime.cli.commands.log.LogCommand
import io.boxtime.cli.commands.report.ReportCommand
import io.boxtime.cli.commands.tag.TagCommand
import io.boxtime.cli.commands.task.TaskCommand
import org.springframework.stereotype.Component
import picocli.CommandLine.Command

@Component
@Command(
    name = "boxtime", mixinStandardHelpOptions = true,
    versionProvider = BoxtimeVersionProvider::class,
    subcommands = [
        TaskCommand::class,
        LogCommand::class,
        ReportCommand::class,
        TagCommand::class,
        AlfredCommand::class,
    ]
)
class BoxtimeCommand(
)