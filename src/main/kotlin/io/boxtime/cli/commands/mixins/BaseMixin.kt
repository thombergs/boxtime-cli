package io.boxtime.cli.commands.mixins

import picocli.CommandLine.Command
import picocli.CommandLine.Option

@Command(
    mixinStandardHelpOptions = true
)
open class BaseMixin

