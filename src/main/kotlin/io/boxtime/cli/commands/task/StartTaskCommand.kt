package io.boxtime.cli.commands.task

import io.boxtime.cli.commands.mixins.BaseCommand
import io.boxtime.cli.config.ApplicationFactory
import org.springframework.stereotype.Component
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters

@Component
@Command(
    name = "start",
    description = ["Start tracking a task."]
)
class StartTaskCommand(
    applicationFactory: ApplicationFactory
) : BaseCommand(applicationFactory) {

    @Parameters(index = "0", description = ["The first couple characters of the task ID."])
    lateinit var taskId: String

    override fun call(): Int {
        getApplication().startTask(taskId)
        return 0
    }

}