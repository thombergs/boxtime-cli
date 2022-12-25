package io.boxtime.cli.commands.task

import io.boxtime.cli.application.Application
import org.springframework.stereotype.Component
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import java.util.concurrent.Callable

@Component
@Command(name = "list", description = ["List the tasks in the database."])
class ListTasksCommand(
    private val application: Application
) : Callable<Int> {

    override fun call(): Int {
        application.listTasks()
        return 0
    }

}