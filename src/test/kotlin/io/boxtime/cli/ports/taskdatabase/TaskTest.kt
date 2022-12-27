package io.boxtime.cli.ports.taskdatabase

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TaskTest {

    @Test
    fun generatesTaskId() {
        val task = Task("Foo")
        println("created task with id ${task.id}")
        assertThat(task.id).isNotNull
        assertThat(task.id.length).isEqualTo(8)
    }

    @Test
    fun extractsTags() {
        val task = Task("Foo with a #tag1 and another #tag2", extractTags = true)
        assertThat(task.tags.map { it.name })
            .contains("tag1")
            .contains("tag2")
        assertThat(task.title).isEqualTo("Foo with a and another")
    }

}