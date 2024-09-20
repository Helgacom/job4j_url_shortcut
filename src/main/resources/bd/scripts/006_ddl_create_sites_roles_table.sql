CREATE TABLE sites_roles
(
    role_id INT REFERENCES roles(id),
    site_id BIGINT REFERENCES sites(id)
);