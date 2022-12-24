package io.boxtime.cli.commands.log

import io.boxtime.cli.application.Application
import org.springframework.stereotype.Component
import picocli.CommandLine.Command
import java.util.concurrent.Callable

@Component
@Command(name = "reset", mixinStandardHelpOptions = true, description = ["Delete all time logs."])
class ResetLogsCommand(
    private val application: Application
) : Callable<Int> {

    override fun call(): Int {
        application.resetTasks()
        return 0
    }

}