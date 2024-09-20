create table statistic (
    id   bigserial primary key,
    callCount serial,
    path varchar not null unique,
    link_id bigint references links(id)
);