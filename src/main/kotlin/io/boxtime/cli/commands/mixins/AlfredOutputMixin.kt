package io.boxtime.cli.commands.mixins

import picocli.CommandLine.Command
import picocli.CommandLine.Option

@Command
open class AlfredOutputMixin {

    @Option(
        names = ["-a", "--alfred"],
        description = [
            "Print any output in Alfred-compatible JSON."]
    )
    var alfred: Boolean = false
}

