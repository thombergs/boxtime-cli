package io.boxtime.cli.commands.task

import io.boxtime.cli.application.Application
import org.springframework.stereotype.Component
import picocli.CommandLine.Command
import java.util.concurrent.Callable

@Component
@Command(name = "reset", description = ["Delete all tasks."])
class ResetTasksCommand(
    private val application: Application
) : Callable<Int> {

    override fun call(): Int {
        application.resetTasks()
        return 0
    }

}