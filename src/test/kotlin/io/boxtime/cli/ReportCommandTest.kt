package io.boxtime.cli

import io.boxtime.cli.commands.report.ReportCommand
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
class ReportCommandTest {

	@Autowired
	lateinit var factory: IFactory

	@Autowired
	lateinit var addTaskCommand: AddTaskCommand

	@Autowired
	lateinit var reportCommand: ReportCommand

	@Autowired
	lateinit var logCommand: LogTaskCommand

	@Autowired
	lateinit var startTaskCommand: StartTaskCommand

	@Autowired
	lateinit var taskDatabase: TaskDatabase

	@Test
	fun listsReport() {

		CommandLine(addTaskCommand, factory)
			.execute("--unit=km", "Running")

		val runningTask = taskDatabase.listTasks()
			.firstOrNull { it.title == "Running" }

		CommandLine(logCommand, factory)
			.execute(runningTask!!.id, "10")

		CommandLine(addTaskCommand, factory)
			.execute("--unit=seconds", "Load the dishwasher")

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

		CommandLine(addTaskCommand, factory)
			.execute("--unit=reps", "Stretches")

		val stretchingTask = taskDatabase.listTasks()
			.firstOrNull { it.title == "Stretches" }

		CommandLine(logCommand, factory)
			.execute(stretchingTask!!.id, "30")

		CommandLine(reportCommand, factory)
			.execute()

	}

}
