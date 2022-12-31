package io.boxtime.cli.commands

import picocli.CommandLine.IVersionProvider

class BoxtimeVersionProvider : IVersionProvider {
    override fun getVersion(): Array<String> {
        val input = this::class.java.getResourceAsStream("/version.txt")
        return arrayOf(
            input.bufferedReader()
                .readLine()
        )
    }
}