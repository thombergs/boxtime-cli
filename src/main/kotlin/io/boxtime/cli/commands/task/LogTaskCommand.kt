package io.boxtime.cli.commands.task

import io.boxtime.cli.commands.mixins.BaseCommand
import io.boxtime.cli.config.ApplicationFactory
import org.springframework.stereotype.Component
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters

@Component
@Command(
    name = "log",
    description = ["Log work on a task."]
)
class LogTaskCommand(
    applicationFactory: ApplicationFactory
) : BaseCommand(applicationFactory) {

    @Parameters(index = "0", description = ["The ID of the task to log."])
    lateinit var taskId: String

    @Parameters(index = "1", description = [
        "The work to log. Input format depends on the unit the task is measured in.",
        "Valid input if unit is 'seconds': '60', '1h', '15m', '45s', '1h15m45s'.",
        "Valid input for other units: '1', '15.3', '0.33'."
    ])
    lateinit var completion: String

    override fun call(): Int {
        getApplication().logTask(taskId, completion)
        return 0
    }

}