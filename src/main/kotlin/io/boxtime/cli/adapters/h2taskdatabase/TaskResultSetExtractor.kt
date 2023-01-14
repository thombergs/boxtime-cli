package io.boxtime.cli.adapters.h2taskdatabase

import org.springframework.jdbc.core.ResultSetExtractor
import java.sql.Date
import java.sql.ResultSet

class TaskResultSetExtractor : ResultSetExtractor<List<TaskEntity>> {
    override fun extractData(resultSet: ResultSet): List<TaskEntity>? {
        // collect the rows
        val flatTasks = mutableListOf<TaskEntity>()
        while (resultSet.next()) {
            val plannedDate: Date? = resultSet.getDate(5)
            val tagId: String? = resultSet.getString(6)
            flatTasks.add(
                TaskEntity(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getTimestamp(3).toLocalDateTime(),
                    resultSet.getString(4),
                    plannedDate?.toLocalDate(),
                    if (tagId == null) setOf() else setOf(TagRef(tagId))
                )
            )
        }

        // group by taskId and join tags
        return flatTasks.groupBy { it.id }
            .map {
                it.value.reduce { left, right ->
                    TaskEntity(
                        left.id,
                        left.title,
                        left.created,
                        left.unit,
                        left.planned,
                        left.tags + right.tags
                    )
                }
            }
    }

}