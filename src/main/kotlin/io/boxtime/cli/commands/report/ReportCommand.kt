package io.boxtime.cli.commands.report

import io.boxtime.cli.commands.mixins.BaseCommand
import io.boxtime.cli.config.ApplicationFactory
import org.springframework.stereotype.Component
import picocli.CommandLine.Command

@Component
@Command(
    name = "report",
    description = ["Output a report of the tasks you have tracked."]
)
class ReportCommand(
    applicationFactory: ApplicationFactory
) : BaseCommand(applicationFactory) {

    override fun call(): Int {
        getApplication().report()
        return 0
    }

}