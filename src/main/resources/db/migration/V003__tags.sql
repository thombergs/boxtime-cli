create table tag(
    id varchar(36) not null,
    name varchar(100) unique not null,
    primary key (id)
);

create table task_tag(
    id integer auto_increment,
    task_id varchar(36) not null,
    tag_id varchar(36) not null
);

create unique index tag_index on task_tag(
    task_id,
    tag_id
);