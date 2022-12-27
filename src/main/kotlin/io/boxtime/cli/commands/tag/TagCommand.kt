package io.boxtime.cli.commands.tag

import org.springframework.stereotype.Component
import picocli.CommandLine.Command

@Component
@Command(
    name = "tag",
    subcommands = [
        ListTagsCommand::class,
    ],
    mixinStandardHelpOptions = true,
    description = ["Commands to manage your tags."]
)
class TagCommand(
)