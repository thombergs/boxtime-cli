package io.boxtime.cli.commands.task

import org.springframework.stereotype.Component
import picocli.CommandLine.Command

@Component
@Command(
    name = "task",
    subcommands = [
        AddTaskCommand::class,
        ListTasksCommand::class,
        ResetTasksCommand::class,
    ],
    description = ["Commands to manage your tasks."]
)
class TaskCommand(
)