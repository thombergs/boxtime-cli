package io.boxtime.cli.commands.track

import io.boxtime.cli.commands.mixins.BaseCommand
import io.boxtime.cli.config.ApplicationFactory
import org.springframework.stereotype.Component
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters

@Component
@Command(
    name = "log",
    description = ["Log time for a task."]
)
class LogTaskCommand(
    applicationFactory: ApplicationFactory
) : BaseCommand(applicationFactory) {

    @Parameters(index = "0", description = ["The ID of the task to log."])
    lateinit var taskId: String

    @Parameters(index = "1", description = ["The duration to log. Examples: '1h', '15m', '45s', '1h15m45s'."])
    lateinit var durationString: String

    override fun call(): Int {
        getApplication().logTask(taskId, durationString)
        return 0
    }

}