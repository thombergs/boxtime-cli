package io.boxtime.cli

import io.boxtime.cli.commands.task.PlanTaskCommand
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
class PlanTaskCommandTest {

    @Autowired
    lateinit var factory: IFactory

    @Autowired
    lateinit var command: PlanTaskCommand

    @Autowired
    lateinit var taskDatabase: TaskDatabase

    @Test
    fun taskStarted() {

        val task = Task("Reticulate splines")
        taskDatabase.addTask(task)

        CommandLine(command, factory)
            .execute(task.id, "tomorrow")

        val plannedTask = taskDatabase.findTaskById(task.id)
        assertThat(plannedTask!!.planned).isNotNull

    }

}
