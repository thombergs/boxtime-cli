package io.boxtime.cli.adapters.alfred

import org.junit.jupiter.api.Test

class AlfredOutputTest {

    @Test
    fun error() {
        val output = AlfredOutput()
        output.error(RuntimeException("BWAAAH!"))
        // TODO: assert output is correct
    }

}