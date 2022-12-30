package io.boxtime.cli.commands.task

import io.boxtime.cli.commands.mixins.BaseCommand
import io.boxtime.cli.config.ApplicationFactory
import org.springframework.stereotype.Component
import picocli.CommandLine
import picocli.CommandLine.Command

@Component
@Command(
    name = "list",
    description = ["List the tasks in the database."]
)
class ListTasksCommand(
    applicationFactory: ApplicationFactory
) : BaseCommand(applicationFactory) {

    @CommandLine.Option(
        names = ["-f", "--filter"],
        description = [
            "Filter string applied to the task title and tags."]
    )
    var filter: String? = null

    @CommandLine.Option(
        names = ["-c", "--count"],
        description = [
            "Maximum number of tasks to return (default: 10)."]
    )
    var count: Int = 10

    override fun call(): Int {
        getApplication().listTasks(count, filter)
        return 0
    }

}