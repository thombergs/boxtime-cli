package io.boxtime.cli

import io.boxtime.cli.commands.task.LogTaskCommand
import io.boxtime.cli.ports.taskdatabase.Task
import io.boxtime.cli.ports.taskdatabase.TaskDatabase
import io.boxtime.cli.ports.tasklogger.TaskLogger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import picocli.CommandLine
import picocli.CommandLine.IFactory

@SpringBootTest
@ActiveProfiles("test")
class LogTaskCommandTest {

    @Autowired
    lateinit var factory: IFactory

    @Autowired
    lateinit var command: LogTaskCommand

    @Autowired
    lateinit var taskDatabase: TaskDatabase

    @Autowired
    lateinit var taskLogger: TaskLogger

    @Test
    fun taskStarted() {

        val task = Task("Reticulate splines")
        taskDatabase.addTask(task)

        CommandLine(command, factory)
            .execute(task.id, "1h5m")

        assertThat(taskLogger.getLogEntries())
            .filteredOn { it.taskId == task.id }
            .hasSize(1)
    }

}
