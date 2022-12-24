package io.boxtime.cli

import io.boxtime.cli.ports.taskdatabase.TaskDatabase
import io.boxtime.cli.commands.task.AddTaskCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import picocli.CommandLine
import picocli.CommandLine.IFactory

@SpringBootTest
@ActiveProfiles("test")
class AddTaskCommandTest {

	@Autowired
	lateinit var factory: IFactory

	@Autowired
	lateinit var command: AddTaskCommand

	@Autowired
	lateinit var taskDatabase: TaskDatabase

	@Test
	fun taskAdded() {

		CommandLine(command, factory)
			.execute("Load the dishwasher")

		assertThat(taskDatabase.listTasks())
			.filteredOn { it.title == "Load the dishwasher" }
			.hasSize(1)
	}

}
