alter table task
    add unit varchar(20) not null default 'seconds';

alter table log
    alter column duration_in_seconds rename to count;

alter table log
    alter column count set data type numeric(20,2);