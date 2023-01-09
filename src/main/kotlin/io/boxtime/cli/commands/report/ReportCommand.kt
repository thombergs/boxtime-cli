package io.boxtime.cli.commands.report

import io.boxtime.cli.commands.mixins.BaseCommand
import io.boxtime.cli.config.ApplicationFactory
import org.springframework.stereotype.Component
import picocli.CommandLine
import picocli.CommandLine.Command

@Component
@Command(
    name = "report",
    description = ["Output a report of the tasks you have tracked."]
)
class ReportCommand(
    applicationFactory: ApplicationFactory
) : BaseCommand(applicationFactory) {

    @CommandLine.Option(
        names = ["-d", "--days"],
        description = [
            "The number of previous days to include in the report (default 0, meaning just today)."
        ]
    )
    var days: Int = 0

    override fun call(): Int {
        getApplication().report(days)
        return 0
    }

}