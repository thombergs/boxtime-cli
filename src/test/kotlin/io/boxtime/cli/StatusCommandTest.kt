package io.boxtime.cli

import io.boxtime.cli.commands.status.StatusCommand
import io.boxtime.cli.ports.taskdatabase.TaskDatabase
import io.boxtime.cli.commands.task.AddTaskCommand
import io.boxtime.cli.commands.track.LogTaskCommand
import io.boxtime.cli.commands.track.StartTaskCommand
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import picocli.CommandLine
import picocli.CommandLine.IFactory

@SpringBootTest
@ActiveProfiles("test")
class StatusCommandTest {

	@Autowired
	lateinit var factory: IFactory

	@Autowired
	lateinit var addTaskCommand: AddTaskCommand

	@Autowired
	lateinit var statusCommand: StatusCommand

	@Autowired
	lateinit var logCommand: LogTaskCommand

	@Autowired
	lateinit var startTaskCommand: StartTaskCommand

	@Autowired
	lateinit var taskDatabase: TaskDatabase

	@Test
	fun listsStatus() {

		CommandLine(addTaskCommand, factory)
			.execute("Load the dishwasher")

		val dishwasherTask = taskDatabase.listTasks()
			.firstOrNull { it.title == "Load the dishwasher" }

		CommandLine(startTaskCommand, factory)
			.execute(dishwasherTask!!.id)

		Thread.sleep(3000)

		CommandLine(addTaskCommand, factory)
			.execute("--unit=l", "Drink water")

		val waterTask = taskDatabase.listTasks()
			.firstOrNull { it.title == "Drink water" }

		CommandLine(logCommand, factory)
			.execute(waterTask!!.id, "5")

		CommandLine(statusCommand, factory)
			.execute()

	}

}
