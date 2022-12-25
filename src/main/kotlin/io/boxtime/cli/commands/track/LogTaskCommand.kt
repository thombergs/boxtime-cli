package io.boxtime.cli.commands.track

import io.boxtime.cli.application.Application
import org.springframework.stereotype.Component
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import java.util.concurrent.Callable

@Component
@Command(name = "log", description = ["Log time for a task."])
class LogTaskCommand(
    private val application: Application
) : Callable<Int> {

    @Parameters(index = "0", description = ["The ID of the task to log."])
    lateinit var taskId: String

    @Parameters(index = "1", description = ["The duration to log. Examples: '1h', '15m', '45s', '1h15m45s'."])
    lateinit var durationString: String

    override fun call(): Int {
        application.logTask(taskId, durationString)
        return 0
    }

}