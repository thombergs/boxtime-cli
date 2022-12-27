package io.boxtime.cli

import io.boxtime.cli.ports.taskdatabase.TaskDatabase
import io.boxtime.cli.commands.task.AddTaskCommand
import io.boxtime.cli.ports.taskdatabase.Task
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
	fun addsTask() {

		CommandLine(command, factory)
			.execute("Load the dishwasher #ignored")

		assertThat(taskDatabase.listTasks())
			.filteredOn { it.title == "Load the dishwasher #ignored" }
			.hasSize(1)
	}

	@Test
	fun addsTaskWithTags() {

		CommandLine(command, factory)
			.execute("#tag1 Load the #tag2 dishwasher #tag3", "--tags")

		val task = taskDatabase.listTasks()
			.firstOrNull { it.title == "Load the dishwasher" }

		assertThat(task).isNotNull
		assertThat(task!!.tags.map { it.name })
			.contains("tag1")
			.contains("tag2")
			.contains("tag3")

	}

}
