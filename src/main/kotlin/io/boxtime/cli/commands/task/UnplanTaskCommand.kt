package io.boxtime.cli.commands.task

import io.boxtime.cli.commands.mixins.BaseCommand
import io.boxtime.cli.config.ApplicationFactory
import org.springframework.stereotype.Component
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters

@Component
@Command(
    name = "unplan",
    description = ["Remove the planned date for a task."]
)
class UnplanTaskCommand(
    applicationFactory: ApplicationFactory
) : BaseCommand(applicationFactory) {

    @Parameters(index = "0", description = ["The ID of the task to plan."])
    lateinit var taskId: String

    override fun call(): Int {
        getApplication().unplanTask(taskId)
        return 0
    }

}