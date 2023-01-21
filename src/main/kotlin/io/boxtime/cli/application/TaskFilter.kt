package io.boxtime.cli.application

data class TaskFilter(
    /**
     * Searches in the name of the task and its tags.
     */
    val name: String = "",

    /**
     * Only match tasks with one of the specified units.
     */
    val requiredUnits: List<String> = listOf(),

    /**
     * Don't match tasks with any of the specified units.
     */
    val rejectedUnits: List<String> = listOf(),

    /**
     * The number of tasks to return.
     */
    val count: Int = 10,

    /**
     * Whether to show only tasks with a planned date.
     */
    val planned: Boolean = false,
)