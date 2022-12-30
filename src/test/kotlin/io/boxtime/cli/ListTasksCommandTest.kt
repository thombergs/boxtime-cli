package io.boxtime.cli

import io.boxtime.cli.commands.task.AddTaskCommand
import io.boxtime.cli.commands.task.ListTasksCommand
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import picocli.CommandLine
import picocli.CommandLine.IFactory

@SpringBootTest
@ActiveProfiles("test")
class ListTasksCommandTest {

	@Autowired
	lateinit var factory: IFactory

	@Autowired
	lateinit var listTaskCommand: ListTasksCommand

	@Autowired
	lateinit var addTaskCommand: AddTaskCommand

	@Test
	fun listsTasks() {
		CommandLine(addTaskCommand, factory)
			.execute("#tag1 Load the #tag2 dishwasher #tag3", "--tags")

		CommandLine(addTaskCommand, factory)
			.execute("#work Dust the warp core", "--tags")

		CommandLine(addTaskCommand, factory)
			.execute("#fitness Go running", "--tags")

		CommandLine(listTaskCommand, factory)
			.execute("--filter", "Dust")

		// TODO: assert log output is correct
	}

}
