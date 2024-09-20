create table sites (
    id        bigserial primary key,
    login     varchar unique not null,
    password  varchar        not null,
    siteName  varchar unique not null
);