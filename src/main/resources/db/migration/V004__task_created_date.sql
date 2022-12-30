alter table task
    add created_date timestamp not null default CURRENT_TIMESTAMP();

create view TASK_TOUCHED as
select id as task_id, max(last_touched) as last_touched
from (select task1.ID       as id,
             log.START_TIME as last_touched
      from TASK task1
               left outer join
           LOG log on task1.ID = log.TASK_ID
      union
      select task2.ID           as id,
             task2.CREATED_DATE as last_touched
      from TASK task2)
GROUP BY id;