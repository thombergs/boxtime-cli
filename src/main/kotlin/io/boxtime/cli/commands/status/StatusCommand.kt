package io.boxtime.cli.commands.status

import io.boxtime.cli.application.Application
import org.springframework.stereotype.Component
import picocli.CommandLine.Command
import java.util.concurrent.Callable

@Component
@Command(
    name = "status",
    description = ["Output current status."]
)
class StatusCommand(
    private val application: Application
) : Callable<Int> {

    override fun call(): Int {
        application.status()
        return 0
    }

}