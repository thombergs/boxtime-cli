package io.boxtime.cli

import io.boxtime.cli.commands.tag.ListTagsCommand
import io.boxtime.cli.commands.task.AddTaskCommand
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import picocli.CommandLine
import picocli.CommandLine.IFactory

@SpringBootTest
@ActiveProfiles("test")
class ListTagsCommandTest {

	@Autowired
	lateinit var factory: IFactory

	@Autowired
	lateinit var listTagsCommand: ListTagsCommand

	@Autowired
	lateinit var addTaskCommand: AddTaskCommand

	@Test
	fun listsTags() {
		CommandLine(addTaskCommand, factory)
			.execute("#tag1 Load the #tag2 dishwasher #tag3", "--tags")

		CommandLine(listTagsCommand, factory)
			.execute()

		// TODO: assert log output is correct
	}

}
