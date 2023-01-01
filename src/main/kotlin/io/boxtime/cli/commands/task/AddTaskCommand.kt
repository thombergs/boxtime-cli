package io.boxtime.cli.commands.task

import io.boxtime.cli.commands.mixins.BaseCommand
import io.boxtime.cli.config.ApplicationFactory
import org.springframework.stereotype.Component
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters

@Component
@Command(
    name = "add",
    description = ["Add a new task to the database."]
)
class AddTaskCommand(
    applicationFactory: ApplicationFactory
) : BaseCommand(applicationFactory) {

    @Parameters(index = "0", description = ["The title of the task to add."])
    lateinit var title: String

    @CommandLine.Option(
        names = ["-t", "--tags"],
        description = [
            "Interpret #hashtags in the task title as tags."]
    )
    var extractTags: Boolean = false

    @CommandLine.Option(
        names = ["-u", "--unit"],
        description = [
            "The unit in which to measure this task (default: 'seconds')."]
    )
    var unit: String = "seconds"

    override fun call(): Int {
        getApplication().addTask(title, unit, extractTags)
        return 0
    }

}