create table task(
    id              bigint          not null,
    title           varchar(10)     not null,
    description     varchar(50)     not null,
    completed       boolean         default false,
    datecreated     timestamp       default now(),
    dateupdated     timestamp,
    datefinished    timestamp,
    primary key(id)
)
craete sequence task_sequence increment 1 start 1 owned by public.simpleproject.task.id;
create table users(
    id              bigint              not null,
    name            varchar(10)         not null,
    password        varchar(255)        not null,
    email           varchar(50)         not null,
    primary key(id)
);
create sequence user_sequence increment 1 start 1
owned by public.simpleproject.users.id;