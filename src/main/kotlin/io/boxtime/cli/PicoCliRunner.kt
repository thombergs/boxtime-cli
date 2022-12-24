package io.boxtime.cli

import io.boxtime.cli.commands.BoxtimeCommand
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.ExitCodeGenerator
import org.springframework.stereotype.Component
import picocli.CommandLine
import picocli.CommandLine.IFactory

@Component
class PicoCliRunner(
    private val command: BoxtimeCommand,
    private val picoCliFactory: IFactory
) : CommandLineRunner, ExitCodeGenerator {

    private var exitCode: Int = 0

    override fun run(vararg args: String) {
        this.exitCode = CommandLine(command, picoCliFactory).execute(*args)
    }

    override fun getExitCode(): Int {
        return this.exitCode
    }
}