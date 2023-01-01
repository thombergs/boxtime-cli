package io.boxtime.cli.ports.taskdatabase

data class Unit(
    val name: String
) {
    companion object {
        /**
         * By default, tasks are measured in time (i.e. seconds).
         */
        val SECONDS = Unit("seconds")
    }
}
