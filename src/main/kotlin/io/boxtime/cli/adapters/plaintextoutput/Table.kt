package io.boxtime.cli.adapters.plaintextoutput

import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter

class Table(
    private val columns: List<Column>,
) {

    private val columnLengths = IntArray(columns.size)

    init {
        for (i in columns.indices) {
            val column = columns[i]
            columnLengths[i] = column.title.length
        }
    }

    private val rows = mutableListOf<Row>()

    fun addRow(vararg fields: String) {
        this.rows.add(Row(fields.asList()))
        for (i in columns.indices) {
            val field = fields[i]
            if (field.length > columnLengths[i]) {
                columnLengths[i] = field.length
            }
        }
    }

    fun print(out: OutputStream) {
        val bufferedWriter = BufferedWriter(OutputStreamWriter(out))

        // column headers
        for (i in columns.indices) {
            val column = columns[i]
            val columnLength = columnLengths[i]
            bufferedWriter.append("| ${column.title.padEnd(columnLength)} ")
        }
        bufferedWriter.append("|")
        bufferedWriter.newLine()

        // separator line
        for (i in columns.indices) {
            val columnLength = columnLengths[i]
            bufferedWriter.append("| " + "".padEnd(columnLength, '-') + " ")
        }
        bufferedWriter.append("|")
        bufferedWriter.newLine()

        for (row in rows) {
            for (i in columns.indices) {
                val field = row.fields[i]
                val columnLength = columnLengths[i]
                bufferedWriter.append("| ${field.padEnd(columnLength)} ")
            }
            bufferedWriter.append("|")
            bufferedWriter.newLine()
        }

        bufferedWriter.flush()
    }
}

data class Column(
    val title: String
)

data class Row(
    val fields: List<String>
)