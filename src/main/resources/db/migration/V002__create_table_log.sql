create table log(
    id integer auto_increment,
    task_id varchar(36) not null,
    start_time timestamp null,
    end_time timestamp null,
    duration_in_seconds integer null,
    primary key (id)
);