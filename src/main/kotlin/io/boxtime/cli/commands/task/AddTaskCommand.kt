package io.boxtime.cli.commands.task

import io.boxtime.cli.application.Application
import org.springframework.stereotype.Component
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import java.util.concurrent.Callable

@Component
@Command(name = "add", mixinStandardHelpOptions = true, description = ["Add a new task to the database."])
class AddTaskCommand(
    private val application: Application
) : Callable<Int> {

    @Parameters(index = "0", description=["The title of the task to add."])
    lateinit var title: String

    override fun call(): Int {
        application.addTask(title)
        return 0
    }

}