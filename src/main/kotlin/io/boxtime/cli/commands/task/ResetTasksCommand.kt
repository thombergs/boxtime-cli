package io.boxtime.cli.commands.task

import io.boxtime.cli.commands.mixins.BaseCommand
import io.boxtime.cli.config.ApplicationFactory
import org.springframework.stereotype.Component
import picocli.CommandLine.Command

@Component
@Command(
    name = "reset",
    description = ["Delete all tasks."]
)
class ResetTasksCommand(
    applicationFactory: ApplicationFactory
) : BaseCommand(applicationFactory) {

    override fun call(): Int {
        getApplication().resetTasks()
        return 0
    }

}