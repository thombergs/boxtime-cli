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
    var filter: String = ""

    @CommandLine.Option(
        names = ["-c", "--count"],
        description = [
            "Maximum number of tasks to return (default: 10)."]
    )
    var count: Int = 10

    @CommandLine.Option(
        names = ["-u", "--unit"],
        description = [
            "List only tasks with this unit.",
            "Can be used multiple times to combine multiple units with a logical OR.",
            "Examples: 'seconds', 'km', 'reps'."
        ]
    )
    var units: List<String> = listOf()

    @CommandLine.Option(
        names = ["-nu", "--nunit"],
        description = [
            "List only tasks that DO NOT have this unit.",
            "Can be used multiple times to combine multiple units with a logical AND.",
            "Examples: 'seconds', 'km', 'reps'."
        ]
    )
    var notUnits: List<String> = listOf()

    @CommandLine.Option(
        names = ["-p", "--planned"],
        description = [
            "List only tasks that have a planned date today or in the future.",
        ]
    )
    var planned: Boolean = false

    override fun call(): Int {
        getApplication().listTasks(count, filter, units, notUnits, planned)
        return 0
    }

}