create table users
(
    id                bigint auto_increment
        primary key,
    email             varchar(255) not null,
    password          varchar(255) not null,
    nickname          varchar(255) not null,
    user_role         varchar(255) not null,
    created_at        datetime(6)  not null,
    modified_at       datetime(6)  not null,
    profile_image_url varchar(255) null,
    constraint UK_users_email
        unique (email)
);

create table log
(
    id            bigint auto_increment
        primary key,
    trace_id      varchar(255) not null,
    user_id       bigint       not null,
    method        varchar(255) not null,
    request_uri   varchar(255) not null,
    http_status   varchar(255) null,
    error_message varchar(255) null,
    created_at    datetime(6)  not null,
    modified_at   datetime(6)  not null
);

create table todos
(
    id          bigint auto_increment
        primary key,
    user_id     bigint       not null,
    title       varchar(255) not null,
    contents    varchar(255) not null,
    weather     varchar(255) not null,
    created_at  datetime(6)  not null,
    modified_at datetime(6)  not null,
    constraint FK_todos_users
        foreign key (user_id) references users (id)
);

create table comments
(
    id          bigint auto_increment
        primary key,
    user_id     bigint       not null,
    todo_id     bigint       not null,
    contents    varchar(255) not null,
    created_at  datetime(6)  not null,
    modified_at datetime(6)  not null,
    constraint FK_comments_todos
        foreign key (todo_id) references todos (id),
    constraint FK_comments_users
        foreign key (user_id) references users (id)
);

create table managers
(
    id      bigint auto_increment
        primary key,
    user_id bigint not null,
    todo_id bigint not null,
    constraint FK_managers_todos
        foreign key (todo_id) references todos (id),
    constraint FK_managers_users
        foreign key (user_id) references users (id)
);

create index IDX_users_nickname
    on users (nickname);