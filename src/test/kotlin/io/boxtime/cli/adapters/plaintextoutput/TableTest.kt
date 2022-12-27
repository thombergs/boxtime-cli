package io.boxtime.cli.adapters.plaintextoutput

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream

class TableTest {

    @Test
    fun test() {
        val table = Table(
            listOf(
                Column("Foo"),
                Column("Bar")
            )
        )

        table.addRow("A rather long field", "A normal field")
        table.addRow("A really, really, really long field", "A normal field")
        table.addRow("short", "short")

        val out = ByteArrayOutputStream()

        table.print(out)
        assertThat(out.toString().trim()).isEqualTo(
            """
            | Foo                                 | Bar            |
            | ----------------------------------- | -------------- |
            | A rather long field                 | A normal field |
            | A really, really, really long field | A normal field |
            | short                               | short          |
            """
                .trimIndent()
                .trim()
        )
    }

}