create table if not exists task(
    id              bigint          not null,
    title           varchar(10)     not null,
    description     varchar(50)     not null,
    completed       boolean         default false,
    datecreated     timestamp       default now(),
    dateupdated     timestamp,
    datefinished    timestamp,
    userid          bigint          not null,
    constraint pk_task primary key(id),
    constraint fk_user foreign key(userid) references users(id)
)
create sequence if not exists task_sequence increment 1 start 1 owned by task.id;
create table users(
    id              bigint              not null,
    name            varchar(10)         not null,
    password        varchar(255)        not null,
    email           varchar(50)         not null,
    constraint pk_user primary key(id)
);
create sequence if not exists user_sequence increment 1 start 1
owned by users.id;