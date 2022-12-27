package io.boxtime.cli.ports.taskdatabase

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TaskTest {

    @Test
    fun generateTaskId() {
        val task = Task("Foo")
        println("created task with id ${task.id}")
        assertThat(task.id).isNotNull
        assertThat(task.id.length).isEqualTo(8)
    }

}