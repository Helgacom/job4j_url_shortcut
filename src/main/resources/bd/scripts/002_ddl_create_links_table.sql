create table links (
    id bigserial primary key,
    url          varchar unique not null,
    convertedUrl varchar unique,
    site_id bigint references sites(id)
);