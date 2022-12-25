package io.boxtime.cli.commands.track

import io.boxtime.cli.application.Application
import org.springframework.stereotype.Component
import picocli.CommandLine.Command
import java.util.concurrent.Callable

@Component
@Command(name = "stop", description = ["Stop tracking."])
class StopTaskCommand(
    private val application: Application
) : Callable<Int> {

    override fun call(): Int {
        application.stopTask()
        return 0
    }

}