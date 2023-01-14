package io.boxtime.cli.commands.task

import io.boxtime.cli.commands.mixins.BaseCommand
import io.boxtime.cli.config.ApplicationFactory
import org.springframework.stereotype.Component
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters

@Component
@Command(
    name = "plan",
    description = ["Plan a task for a specified day."]
)
class PlanTaskCommand(
    applicationFactory: ApplicationFactory
) : BaseCommand(applicationFactory) {

    @Parameters(index = "0", description = ["The ID of the task to plan."])
    lateinit var taskId: String

    @Parameters(
        index = "1", description = [
            "The day to plan the task for. Specify a date relative to today.",
            "Examples: 'tomorrow', '3d', '2w', 'nextweek', 'nextmonth'."
        ]
    )
    lateinit var plannedDate: String

    override fun call(): Int {
        getApplication().planTask(taskId, plannedDate)
        return 0
    }

}