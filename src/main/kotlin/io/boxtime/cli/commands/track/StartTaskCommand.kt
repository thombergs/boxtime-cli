package io.boxtime.cli.commands.track

import io.boxtime.cli.application.Application
import org.springframework.stereotype.Component
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import java.util.concurrent.Callable

@Component
@Command(name = "start", description = ["Start tracking a task."])
class StartTaskCommand(
    private val application: Application
) : Callable<Int> {

    @Parameters(index = "0", description=["The ID of the task to track."])
    lateinit var taskId: String

    override fun call(): Int {
        application.startTask(taskId)
        return 0
    }

}