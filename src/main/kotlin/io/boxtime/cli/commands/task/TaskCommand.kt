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
        LogTaskCommand::class,
        StartTaskCommand::class,
        StopTaskCommand::class,
        PlanTaskCommand::class,
        UnplanTaskCommand::class
    ],
    mixinStandardHelpOptions = true,
    description = ["Commands to manage your tasks."]
)
class TaskCommand(
)