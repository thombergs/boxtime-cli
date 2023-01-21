package io.boxtime.cli.adapters.h2taskdatabase

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface TaskRepository : CrudRepository<TaskEntity, String> {

    fun findByIdStartingWith(id: String): List<TaskEntity>

    @Query(
        """
        select task.ID,
               task.TITLE,
               task.CREATED_DATE,
               task.UNIT,
               task.PLANNED_DATE,
               tt.TAG_ID as TAG_ID
        from TASK task
                 join
             TASK_TOUCHED touched on task.ID = touched.TASK_ID
                 left outer join
             TASK_TAG tt on task.ID = tt.TASK_ID
                 left outer join
             TAG tag on tt.TAG_ID = tag.ID
        where 
          task.ID in (select TASK_ID from TASK_TOUCHED order by LAST_TOUCHED desc limit :count)
          and (
            LOWER(task.TITLE) like concat('%', concat(LOWER(:filter), '%'))
            or LOWER(tag.NAME) like concat('%', concat(LOWER(:filter), '%'))
          )
          and (
            ((:requiredUnits) is null OR task.UNIT in (:requiredUnits))
            and ((:rejectedUnits) is null OR task.UNIT not in (:rejectedUnits))
            and ((:planned) = false OR task.PLANNED_DATE is not null)
          )
        order by task.PLANNED_DATE asc nulls last, touched.LAST_TOUCHED desc
    """, resultSetExtractorClass = TaskResultSetExtractor::class
    )
    fun findTasks(
        @Param("count") count: Int,
        @Param("filter") filter: String,
        @Param("requiredUnits") requiredUnits: List<String>? = null,
        @Param("rejectedUnits") rejectedUnits: List<String>? = null,
        @Param("planned") planned: Boolean = false
    ): List<TaskEntity>

}

